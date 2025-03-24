package cn.com.choy.dbguard.dao;

import cn.com.choy.dbguard.entity.po.DataSourcePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据源管理dao接口
 *
 * @author choyrunyu
 * @since 2025/03/16
 */
@Repository
public interface DataSourceDao extends JpaRepository<DataSourcePO, String> {

    /**
     * 分页查询数据源
     *
     * @param searchKey 关键词搜索
     * @return 返回分页数据
     */
    List<DataSourcePO> findByDataSourceNameLike(String searchKey);

    /**
     * 根据数据源名称查询数据源是否存在
     *
     * @param dataSourceName 数据源名称
     * @return 返回数据源是否存在
     */
    boolean existsByDataSourceName(String dataSourceName);

}
