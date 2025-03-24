package cn.com.choy.dbguard.service.impl;

import cn.com.choy.dbguard.dao.DataSourceDao;
import cn.com.choy.dbguard.entity.dto.TableFieldDTO;
import cn.com.choy.dbguard.entity.dto.TableInfoDTO;
import cn.com.choy.dbguard.entity.po.DataSourcePO;
import cn.com.choy.dbguard.entity.query.SqlQuery;
import cn.com.choy.dbguard.enums.DBTypeEnums;
import cn.com.choy.dbguard.exception.DBGuardException;
import cn.com.choy.dbguard.service.DBService;
import cn.com.choy.dbguard.service.IDbGuardService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 数据库相关接口
 *
 * @author choyrunyu
 * @since 2025/03/18
 */
@Service
public class DbGuardServiceImpl implements IDbGuardService {

    private static final Logger log = LoggerFactory.getLogger(DbGuardServiceImpl.class);

    @Resource
    private DataSourceDao dataSourceDao;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private DBService dbService;

    /**
     * 获取数据库表列表
     *
     * @param dataSourceId 数据源id
     * @param searchKey    搜索
     * @return 返回数据库表列表
     */
    @Override
    public List<TableInfoDTO> listTables(String dataSourceId, String searchKey) {
        DataSourcePO dataSource = dataSourceDao.findById(dataSourceId).orElseThrow(() -> new DBGuardException("数据源不存在"));
        String sql;
        switch (DBTypeEnums.getDBTypeEnums(dataSource.getDbType())) {
            case MYSQL -> {
                sql = """
                                SELECT TABLE_NAME as tableName, TABLE_COMMENT as tableComment
                                FROM INFORMATION_SCHEMA.TABLES
                                WHERE TABLE_SCHEMA = DATABASE()
                        """;
                if (StrUtil.isNotBlank(searchKey)) {
                    sql += " AND (TABLE_NAME LIKE concat('%', :searchKey, '%') or TABLE_COMMENT like concat('%', :searchKey, '%'))";
                }
            }
            case ORACLE -> {
                sql = "SELECT TABLE_NAME AS tableName, COMMENTS AS tableComment FROM USER_TAB_COMMENTS ";
                if (StrUtil.isNotBlank(searchKey)) {
                    sql += " where instr(TABLE_NAME, :searchKey) > 0 or instr(COMMENTS, :searchKey) > 0";
                }
            }
            default -> throw new DBGuardException("不支持的数据库类型");
        }
        ;

        return query(dataSourceId, sql, Collections.singletonMap("searchKey", searchKey), TableInfoDTO.class);
    }

    /**
     * 获取数据库表字段列表
     *
     * @param dataSourceId 数据源id
     * @param tableName    表名
     * @return 返回数据库表字段列表
     */
    @Override
    public List<TableFieldDTO> tableFields(String dataSourceId, String tableName) {
        DataSourcePO dataSource = dataSourceDao.findById(dataSourceId).orElseThrow(() -> new DBGuardException("数据源不存在"));

        Map<String, Object> params = Collections.singletonMap("tableName", tableName);
        String sql = switch (DBTypeEnums.getDBTypeEnums(dataSource.getDbType())) {
            case MYSQL -> """
                    SELECT
                        TABLE_NAME AS tableName,
                        COLUMN_NAME AS fieldName,
                        COLUMN_COMMENT AS fieldComment,
                        DATA_TYPE AS fieldType,
                        CHARACTER_MAXIMUM_LENGTH AS length,
                        NUMERIC_PRECISION AS dataPrecision,
                        NUMERIC_SCALE AS decimalScale,
                        IS_NULLABLE AS nullable,
                        IF(COLUMN_KEY = 'PRI', 'YES', 'NO') AS primaryKey
                    FROM INFORMATION_SCHEMA.COLUMNS
                    WHERE TABLE_SCHEMA = DATABASE()
                    AND TABLE_NAME = :tableName
                    ORDER BY ORDINAL_POSITION
                    """;
            case ORACLE -> """
                    SELECT col.TABLE_NAME                                                AS tableName,
                           col.COLUMN_NAME                                               AS fieldName,
                           COALESCE(comm.COMMENTS, '无')                                 AS fieldComment,
                           col.DATA_TYPE                                                 AS fieldType,
                           col.DATA_LENGTH                                               AS length,
                           col.DATA_PRECISION                                            AS dataPrecision,
                           col.DATA_SCALE                                                AS decimalScale,
                           CASE col.NULLABLE WHEN 'N' THEN 'NO' ELSE 'YES' END           AS nullable,
                           CASE WHEN pk.COLUMN_NAME IS NOT NULL THEN 'YES' ELSE 'NO' END AS primaryKey
                    FROM USER_TAB_COLUMNS col
                             LEFT JOIN USER_COL_COMMENTS comm
                                       ON col.TABLE_NAME = comm.TABLE_NAME
                                           AND col.COLUMN_NAME = comm.COLUMN_NAME
                             LEFT JOIN (SELECT acc.COLUMN_NAME, acc.TABLE_NAME
                                        FROM USER_CONS_COLUMNS acc
                                                 JOIN USER_CONSTRAINTS ac
                                                      ON acc.CONSTRAINT_NAME = ac.CONSTRAINT_NAME
                                        WHERE ac.CONSTRAINT_TYPE = 'P') pk ON col.TABLE_NAME = pk.TABLE_NAME
                        AND col.COLUMN_NAME = pk.COLUMN_NAME
                    WHERE col.TABLE_NAME = :tableName
                    ORDER BY col.COLUMN_ID
                    """;
        };
        return query(dataSourceId, sql, params, TableFieldDTO.class);
    }

    /**
     * 修改表注释
     *
     * @param dataSourceId 数据源id
     * @param tableName    表名
     * @param tableComment 表注释
     * @return 返回结果
     */
    @Override
    public String modifyTableCommit(String dataSourceId, String tableName, String tableComment) {
        DataSourcePO dataSourcePO = dataSourceDao.findById(dataSourceId).orElseThrow(() -> new DBGuardException("数据源不存在"));
        safeTableName(tableName);

        String sql = switch (DBTypeEnums.getDBTypeEnums(dataSourcePO.getDbType())) {
            case MYSQL -> "ALTER TABLE %s COMMENT :tableComment".formatted(tableName);
            case ORACLE -> "COMMENT ON TABLE %s IS '%S'".formatted(tableName, tableComment);
        };

        Map<String, Object> params = Collections.singletonMap("tableComment", tableComment);
        update(dataSourceId, sql, params);
        return "修改成功";
    }

    /**
     * 修改字段注释
     *
     * @param dataSourceId 数据源id
     * @param tableName    表名
     * @param fieldName    字段名
     * @param fieldComment 字段注释
     * @return 返回结果
     */
    @Override
    public String modifyFieldCommit(String dataSourceId, String tableName, String fieldName, String fieldComment) {
        DataSourcePO dataSourcePO = dataSourceDao.findById(dataSourceId).orElseThrow(() -> new DBGuardException("数据源不存在"));

        Map<String, Object> params = new HashMap<>();
        params.put("tableName", tableName);
        params.put("fieldName", fieldName);
        params.put("fieldComment", fieldComment);

        String sql = switch (DBTypeEnums.getDBTypeEnums(dataSourcePO.getDbType())) {
            case MYSQL -> {
                // 获取字段类型
                String fieldType;
                try {
                    DynamicDataSourceContextHolder.push(dataSourceId);
                    String getFieldTypeSql = """
                                SELECT COLUMN_TYPE
                                FROM INFORMATION_SCHEMA.COLUMNS
                                WHERE TABLE_SCHEMA = DATABASE()
                                AND TABLE_NAME = :tableName
                                AND COLUMN_NAME = :fieldName
                            """;
                    fieldType = namedParameterJdbcTemplate.queryForObject(getFieldTypeSql, params, String.class);
                } catch (Exception exception) {
                    log.error("获取字段类型失败 - {}", exception.getMessage(), exception);
                    DynamicDataSourceContextHolder.poll();
                    throw new DBGuardException("获取字段类型失败");
                }

                // 修改字段注释时同时指定字段类型
                yield "ALTER TABLE %s MODIFY COLUMN %s %s COMMENT '%s'".formatted(tableName, fieldName, fieldType, fieldComment);
            }
            case ORACLE -> "COMMENT ON COLUMN %s.%s IS '%s'".formatted(tableName, fieldName, fieldComment);
        };

        update(dataSourceId, sql, params);
        return "修改成功";
    }

    /**
     * 执行sql
     *
     * @param dataSourceId 数据源id
     * @param sqlQuery     查询sql
     * @return 返回结果
     */
    @Override
    public Object querySql(String dataSourceId, SqlQuery sqlQuery) {
        dbService.loadDataSource(dataSourceId);
        try {
            DynamicDataSourceContextHolder.push(dataSourceId);
            return jdbcTemplate.query(sqlQuery.getSql(), resultSet -> {
                List<Map<String, Object>> resultList = new ArrayList<>();
                var metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(i);
                        row.put(columnName, value);
                    }
                    resultList.add(row);
                }
                return resultList;
            });
        } catch (Exception exception) {
            log.error("执行异常 - {}", exception.getMessage(), exception);
            throw new DBGuardException("执行异常 - " + exception.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * 执行update操作
     *
     * @param dataSourceId 数据源id
     * @param sqlQuery     执行sql
     * @return 返回结果
     */
    @Override
    public Object updateSql(String dataSourceId, SqlQuery sqlQuery) {
        dbService.loadDataSource(dataSourceId);
        try {
            DynamicDataSourceContextHolder.push(dataSourceId);
            return jdbcTemplate.update(sqlQuery.getSql());
        } catch (Exception exception) {
            log.error("执行异常 - {}", exception.getMessage(), exception);
            throw new DBGuardException("执行异常 - " + exception.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * 执行查询操作
     *
     * @param dataSourceId 数据源id
     * @param sql          执行sql
     * @param params       入参
     * @param clazz        返回类型
     * @param <T>          返回类型
     * @return 返回结果
     */
    private <T> List<T> query(String dataSourceId, String sql, Map<String, Object> params, Class<T> clazz) {
        dbService.loadDataSource(dataSourceId);
        try {
            DynamicDataSourceContextHolder.push(dataSourceId);
            log.info("执行的sql：{}, 参数： {}", sql, params);
            return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(clazz));
        } catch (Exception exception) {
            log.error("执行异常 - {}", exception.getMessage(), exception);
            throw new DBGuardException("执行异常 - " + exception.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * 执行update操作
     *
     * @param dataSourceId 数据源id
     * @param sql          执行sql
     * @param params       入参
     */
    private void update(String dataSourceId, String sql, Map<String, Object> params) {
        dbService.loadDataSource(dataSourceId);
        try {
            DynamicDataSourceContextHolder.push(dataSourceId);
            log.info("执行的sql：{}, 参数： {}", sql, params);
            namedParameterJdbcTemplate.update(sql, params);
        } catch (Exception exception) {
            log.error("执行异常 - {}", exception.getMessage(), exception);
            throw new DBGuardException("执行异常 - " + exception.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * 校验表明
     *
     * @param tableName 表名
     */
    private void safeTableName(String tableName) {
        if (!tableName.matches("^[a-zA-Z0-9_]+$")) {
            throw new DBGuardException("非法表名");
        }
    }


}
