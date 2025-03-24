package cn.com.choy.dbguard.service;


import cn.com.choy.dbguard.dao.DataSourceDao;
import cn.com.choy.dbguard.entity.po.DataSourcePO;
import cn.com.choy.dbguard.enums.DBTypeEnums;
import cn.com.choy.dbguard.exception.DBGuardException;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 数据源切换工具类
 *
 * @author choyrunyu
 * @since 2025/03/23
 */
@Slf4j
@Component
public class DBService {

    @Resource
    private DataSource dataSource;
    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;
    @Resource
    private DefaultDataSourceCreator defaultDataSourceCreator;
    @Resource
    private DataSourceDao dataSourceDao;

    public void loadDataSource(String dataSourceId) {
        DataSourcePO dataSource = dataSourceDao.findById(dataSourceId).orElseThrow(() -> new DBGuardException("数据源不存在"));
        loadDataSource(dataSource);
    }

    public void loadDataSource(DataSourcePO dataSourcePO) {
        if (!isDataSourceExist(dataSourcePO.getId())) {
            try {
                DataSourceProperty dataSourceProperty = new DataSourceProperty();
                dataSourceProperty.setUrl(dataSourcePO.getDbUrl());
                dataSourceProperty.setDriverClassName(DBTypeEnums.getDBTypeEnums(dataSourcePO.getDbType()).getClassName());
                dataSourceProperty.setUsername(dataSourcePO.getUsername());
                dataSourceProperty.setPassword(dataSourcePO.getPassword());
                DataSource dataSource = defaultDataSourceCreator.createDataSource(dataSourceProperty);
                dynamicRoutingDataSource.addDataSource(dataSourcePO.getId(), dataSource);
            } catch (Exception exception) {
                log.warn("加载数据源失败 - {} - {}", dataSourcePO, exception.getMessage(), exception);
                throw new DBGuardException("加载数据源失败");
            }
        }
    }

    /**
     * 判断数据源是否存在
     *
     * @param dataSourceKey 数据库key
     * @return 返回是否存在
     */
    private boolean isDataSourceExist(String dataSourceKey) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getDataSources().containsKey(dataSourceKey);
    }

}
