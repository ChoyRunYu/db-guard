package cn.com.choy.dbguard.entity.dto;


import lombok.Data;

/**
 * 表信息实体
 *
 * @author choyrunyu
 * @since 2025/03/19
 */
@Data
public class TableInfoDTO {


    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

}
