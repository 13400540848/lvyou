package org.ume.school.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.ContextResource;
//import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bluesimon.wbf.config.WbfWebConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 是默认自动启动web配置
 * Web Basic Framework
 * Created by Django on 2017/12/5.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.bluesimon.wbf", "org.ume.school"})
@EnableJpaRepositories(basePackages = {"org.ume.school", "com.bluesimon.wbf.modules"})
@EnableTransactionManagement
public class WebConfig extends WbfWebConfig {
    @Override
    @Bean
    public DataSource dataSource() {
        try {
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(dataSourceConfig.getDriverClass());
            dataSource.setJdbcUrl(dataSourceConfig.getUrl());
            dataSource.setUser(dataSourceConfig.getUser());
            dataSource.setPassword(dataSourceConfig.getPassword());
            dataSource.setAcquireIncrement(dataSourceConfig.getAcquireIncrement());
            dataSource.setAcquireRetryAttempts(dataSourceConfig.getAcquireRetryAttempts());
            dataSource.setAcquireRetryDelay(dataSourceConfig.acquireRetryDelay);
            dataSource.setAutoCommitOnClose(dataSourceConfig.isAutoCommitOnClose());
            dataSource.setInitialPoolSize(dataSourceConfig.getInitialPoolSize());
            dataSource.setMaxIdleTime(dataSourceConfig.getMaxIdleTime());
            dataSource.setMaxPoolSize(dataSourceConfig.getMaxPoolSize());
            dataSource.setMaxStatements(dataSourceConfig.getMaxStatements());
            dataSource.setMaxStatementsPerConnection(dataSourceConfig.getMaxStatementsPerConnection());
            dataSource.setNumHelperThreads(dataSourceConfig.getNumHelperThreads());
            dataSource.setPropertyCycle(dataSourceConfig.getPropertyCycle());
            return dataSource;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

}
