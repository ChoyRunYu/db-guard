# db-guard

-----
<p align="center"> 
    <img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk"/>
    <img src="https://img.shields.io/badge/Spring%20Boot-3.4.3-blue.svg" alt="spring boot"/>
    <img src="https://img.shields.io/badge/spring%20ai-1.0.0%20M6-blue.svg" alt="spring-ai" />
    <img src="https://img.shields.io/badge/Author-Choy RunYu-pink.svg" alt="author" />
</p>


一款为数据库提供ai接入能力的工具

### 已开发功能

- 维护数据库连接
- 支持数据库连接池
- 支持ORACLE、MYSQL
- 支持MCP Server调用
- 支持API调用
- 支持表以及表结构查询
- 支持表及表结构注释维护

### MCP Server 路径

```
localhost:8083/sse
``` 

### api 路径

```
执行查询sql：/db_guard/database/query_sql/{数据源id}
执行更新sql：/db_guard/database/update_sql/{数据源id}
```


### docker部署
```shell
# 拉取镜像
docker pull registry.cn-hangzhou.aliyuncs.com/choyrunyu/db-guard:1.0

# 启动镜像, 需挂载h2配置文件，否则重新部署后数据会丢失
docekr run -it -d -p 8083:8083 -v 宿主机路径:/root/ --name db-guard registry.cn-hangzhou.aliyuncs.com/choyrunyu/db-guard:1.0

```
