package cn.com.choy.dbguard.entity.dto;


import lombok.Data;

/**
 * 表字段详情
 *
 * @author choyrunyu
 * @since 2025/03/19
 */
@Data
public class TableFieldDTO {


    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段描述
     */
    private String fieldComment;

    /**
     * 长度
     */
    private String length;

    /**
     * 数值精度
     */
    private String dataPrecision;

    /**
     * 小数位数
     */
    private String decimalScale;

    /**
     * 是否允许为空
     */
    private String nullable;

    /**
     * 是否主键
     */
    private String primaryKey;


}
