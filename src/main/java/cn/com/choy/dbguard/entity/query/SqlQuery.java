package cn.com.choy.dbguard.entity.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库sql入参
 *
 * @author choyrunyu
 * @since 2025/03/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SqlQuery {

    /**
     * 执行的sql
     */
    private String sql;

}
