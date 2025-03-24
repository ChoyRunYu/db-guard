package cn.com.choy.dbguard.entity.dto;

import lombok.Data;

/**
 * 标签和名称下拉框
 *
 * @author choyrunyu
 * @since 2025/03/23
 */
@Data
public class LabelValueDTO {


    /**
     * 标签
     */
    private String label;

    /**
     * 值
     */
    private String value;

}
