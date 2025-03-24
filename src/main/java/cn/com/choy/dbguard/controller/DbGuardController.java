package cn.com.choy.dbguard.controller;


import cn.com.choy.dbguard.entity.dto.Result;
import cn.com.choy.dbguard.entity.dto.TableFieldDTO;
import cn.com.choy.dbguard.entity.dto.TableInfoDTO;
import cn.com.choy.dbguard.entity.query.SqlQuery;
import cn.com.choy.dbguard.service.IDbGuardService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据库相关接口
 *
 * @author choyrunyu
 * @since 2025/03/20
 */
@RestController
@RequestMapping("/db_guard/database")
public class DbGuardController {

    @Resource
    private IDbGuardService dbGuardService;

    /**
     * 获取数据库表列表
     *
     * @param dataSourceId 数据源id
     * @param searchKey    搜索
     * @return 返回数据库表列表
     */
    @GetMapping("/list_tables")
    public Result<List<TableInfoDTO>> listTables(String dataSourceId, String searchKey) {
        return Result.success(dbGuardService.listTables(dataSourceId, searchKey));
    }

    /**
     * 获取数据库表字段列表
     *
     * @param dataSourceId 数据源id
     * @param tableName    表名
     * @return 返回数据库表字段列表
     */
    @GetMapping("/table_fields")
    public Result<List<TableFieldDTO>> tableFields(String dataSourceId, String tableName) {
        return Result.success(dbGuardService.tableFields(dataSourceId, tableName));
    }


    /**
     * 修改表注释
     *
     * @param dataSourceId 数据源id
     * @param tableName    表名
     * @param tableComment 表注释
     * @return 返回结果
     */
    @PostMapping("/modify_table_commit")
    public Result<String> modifyTableCommit(String dataSourceId, String tableName, String tableComment) {
        return Result.success(dbGuardService.modifyTableCommit(dataSourceId, tableName, tableComment));
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
    @PostMapping("/modify_field_commit")
    public Result<String> modifyFieldCommit(String dataSourceId, String tableName, String fieldName, String fieldComment) {
        return Result.success(dbGuardService.modifyFieldCommit(dataSourceId, tableName, fieldName, fieldComment));
    }

    /**
     * 执行sql
     *
     * @param dataSourceId 数据源id
     * @param sqlQuery     查询sql
     * @return 返回结果
     */
    @PostMapping("/query_sql/{dataSourceId}")
    public Object querySql(@PathVariable("dataSourceId") String dataSourceId, @RequestBody SqlQuery sqlQuery) {
        return dbGuardService.querySql(dataSourceId, sqlQuery);
    }

    /**
     * 执行update的sql
     *
     * @param dataSourceId 数据源id
     * @param sqlQuery     查询sql
     * @return 返回结果
     */
    @PostMapping("/update_sql/{dataSourceId}")
    public Object updateSql(@PathVariable("dataSourceId") String dataSourceId, @RequestBody SqlQuery sqlQuery) {
        return dbGuardService.updateSql(dataSourceId, sqlQuery);
    }

}
