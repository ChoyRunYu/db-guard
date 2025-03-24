package cn.com.choy.dbguard.service;

import cn.com.choy.dbguard.dao.DataSourceDao;
import cn.com.choy.dbguard.entity.query.SqlQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mcp服务
 *
 * @author choyrunyu
 * @since 2025/03/23
 */
@Slf4j
@Service
public class McpService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private IDbGuardService dbGuardService;
    @Resource
    private DataSourceDao dataSourceDao;

    /**
     * 获取数据库连接列表
     *
     * @return 返回数据库连接列表
     * @throws JsonProcessingException json处理异常
     */
    @Tool(name = "queryDatabaseResource", description = "Get the database resource list")
    public String queryDatabaseResource() throws JsonProcessingException {
        List<Map<String, Object>> collect = dataSourceDao.findAll().stream().map(item -> {
            Map<String, Object> resourceItem = new HashMap<>();
            resourceItem.put("dataSourceId", item.getId());
            resourceItem.put("dataSourceName", item.getDataSourceName());
            resourceItem.put("dataSourceDesc", item.getDataSourceDesc());
            return resourceItem;
        }).toList();
        return objectMapper.writeValueAsString(collect);
    }

    /**
     * 获取数据库所有表
     *
     * @param dataSourceId 数据源id
     * @return 返回数据库所有表
     * @throws JsonProcessingException json处理异常
     */
    @Tool(name = "queryTables", description = "Get the database all table by dataSourceId")
    public String queryAllTable(@ToolParam(description = "Database connection id, it's from the queryDatabaseResource tool dataSourceId") String dataSourceId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dbGuardService.listTables(dataSourceId, null));
    }

    /**
     * 获取数据库所有表
     *
     * @param dataSourceId 数据源id
     * @return 返回数据库所有表
     * @throws JsonProcessingException json处理异常
     */
    @Tool(name = "queryTableField", description = "Get the field detail on database tables by dataSourceId")
    public String queryTableField(@ToolParam(description = "Database connection id, it's from the queryDatabaseResource tool dataSourceId") String dataSourceId,
                                  @ToolParam(description = "The name of the database table that will be obtained") String tableName) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dbGuardService.tableFields(dataSourceId, tableName));
    }

    /**
     * 执行查询sql
     *
     * @param dataSourceId 数据源id
     * @param sql          执行的sql
     * @return 返回查询结果
     * @throws JsonProcessingException json处理异常
     */
    @Tool(name = "querySql", description = "Execute the sql statement on the database by dataSourceId")
    public String querySql(@ToolParam(description = "Database connection id, it's from the queryDatabaseResource tool dataSourceId") String dataSourceId,
                           @ToolParam(description = "The sql statement that will be executed") String sql) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dbGuardService.querySql(dataSourceId, new SqlQuery(sql)));
    }

    /**
     * 执行更新sql
     *
     * @param dataSourceId 数据源id
     * @param sql          执行的sql
     * @return 返回查询结果
     * @throws JsonProcessingException json处理异常
     */
    @Tool(name = "updateSql", description = "Execute the update sql statement on the database by dataSourceId")
    public String updateSql(@ToolParam(description = "Database connection id, it's from the queryDatabaseResource tool dataSourceId") String dataSourceId,
                            @ToolParam(description = "The sql statement that will be executed") String sql) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dbGuardService.updateSql(dataSourceId, new SqlQuery(sql)));
    }


}
