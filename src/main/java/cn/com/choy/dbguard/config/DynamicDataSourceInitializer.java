package cn.com.choy.dbguard.config;

import cn.com.choy.dbguard.dao.DataSourceDao;
import cn.com.choy.dbguard.service.DBService;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动加载数据源
 *
 * @author choyrunyu
 * @since 2025/03/21
 */
@Component
public class DynamicDataSourceInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceInitializer.class);

    @Resource
    private DataSourceDao dataSourceDao;
    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;
    @Resource
    private DBService dbService;


    @Override
    public void run(ApplicationArguments args) {
        dataSourceDao.findAll().forEach(dbService::loadDataSource);
        log.info("数据源： {}", dynamicRoutingDataSource.getDataSources().keySet());
    }
}
