package cn.com.choy.dbguard.entity.dto;


import lombok.Data;

import java.util.List;

/**
 * 分页结果
 *
 * @param <T> 返回的数据类型
 * @author choyrunyu
 * @since 2025/03/19
 */
@Data
public class PageDTO<T> {

    private long total;
    private List<T> data;


    public PageDTO(long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public PageDTO() {
    }

}
