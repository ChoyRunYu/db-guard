package cn.com.choy.dbguard.service;

import cn.com.choy.dbguard.entity.dto.TableFieldDTO;
import cn.com.choy.dbguard.entity.dto.TableInfoDTO;
import cn.com.choy.dbguard.entity.query.SqlQuery;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 数据库相关接口
 *
 * @author choyrunyu
 * @since 2025/03/18
 */
public interface IDbGuardService {

    /**
     * 获取数据库表列表
     *
     * @param dataSourceId 数据源id
     * @param searchKey    搜索
     * @return 返回数据库表列表
     */
    List<TableInfoDTO> listTables(String dataSourceId, String searchKey);

    /**
     * 获取数据库表字段列表
     *
     * @param dataSourceId 数据源id
     * @param tableName    表名
     * @return 返回数据库表字段列表
     */
    List<TableFieldDTO> tableFields(String dataSourceId, String tableName);

    /**
     * 修改表注释
     *
     * @param dataSourceId 数据源id
     * @param tableName    表名
     * @param tableComment 表注释
     * @return 返回结果
     */
    String modifyTableCommit(String dataSourceId, String tableName, String tableComment);

    /**
     * 修改字段注释
     *
     * @param dataSourceId 数据源id
     * @param tableName    表名
     * @param fieldName    字段名
     * @param fieldComment 字段注释
     * @return 返回结果
     */
    String modifyFieldCommit(String dataSourceId, String tableName, String fieldName, String fieldComment);

    /**
     * 执行sql
     *
     * @param dataSourceId 数据源id
     * @param sqlQuery     查询sql
     * @return 返回结果
     */
    Object querySql(@PathVariable("dataSourceId") String dataSourceId, @RequestBody SqlQuery sqlQuery);

    /**
     * 执行修改sql
     *
     * @param dataSourceId 数据源id
     * @param sqlQuery     查询sql
     * @return 返回结果
     */
    Object updateSql(@PathVariable("dataSourceId") String dataSourceId, @RequestBody SqlQuery sqlQuery);
}
