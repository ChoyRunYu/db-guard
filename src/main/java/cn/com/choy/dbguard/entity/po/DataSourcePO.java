package cn.com.choy.dbguard.entity.po;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * 数据库源
 *
 * @author choyrunyu
 * @since 2025/03/16
 */
@Data
@Entity
@Table(name = "datasource")
public class DataSourcePO {


    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 数据源名称
     */
    @Column(name = "data_source_name")
    private String dataSourceName;

    /**
     * 数据源描述
     */
    @Column(name = "data_source_desc")
    private String dataSourceDesc;

    /**
     * 数据库类型
     */
    @Column(name = "db_type")
    private String dbType;

    /**
     * 数据库连接
     */
    @Column(name = "db_url")
    private String dbUrl;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

//    /**
//     * 数据库名
//     */
//    @Column(name = "db_name")
//    private String dbName;


}
