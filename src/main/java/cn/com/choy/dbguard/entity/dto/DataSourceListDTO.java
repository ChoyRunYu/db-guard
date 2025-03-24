package cn.com.choy.dbguard.entity.dto;

import lombok.Data;


/**
 * 数据源列表
 *
 * @author choyrunyu
 * @since 2025/03/18
 */
@Data
public class DataSourceListDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 数据源描述
     */
    private String dataSourceDesc;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 数据库连接
     */
    private String dbUrl;

    /**
     * 数据库用户名
     */
    private String username;

}
