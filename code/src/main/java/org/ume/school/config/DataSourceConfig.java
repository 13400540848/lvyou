package org.ume.school.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Django on 2017/12/5.
 */
@PropertySource("classpath:config/jdbc.properties")
public class DataSourceConfig {
    //TODO 注入无效
    @Value("${jdbc.driverClass}")
    String driverClass = "com.mysql.jdbc.Driver";
    @Value("${jdbc.url}")
    //String url = "jdbc:mysql://127.0.0.1:3306/lvyou?useUnicode=true&amp;characterEncoding=utf-8";
    String url = "jdbc:mysql://192.168.0.182:12306/lvyou?useUnicode=true&amp;characterEncoding=utf-8";
    @Value("${jdbc.user}")
    String user = "root";
    @Value("${jdbc.password}")
    //String password = "123456";
    String password = "gszh8899";
    @Value("${jdbc.acquireIncrement}")
    int acquireIncrement = 10;
    @Value("${jdbc.acquireRetryAttempts}")
    int acquireRetryAttempts = 100;
    @Value("${jdbc.acquireRetryDelay}")
    int acquireRetryDelay = 1000;
    @Value("${jdbc.autoCommitOnClose}")
    boolean autoCommitOnClose = false;
    @Value("${jdbc.initialPoolSize}")
    int initialPoolSize = 20;
    @Value("${jdbc.maxIdleTime}")
    int maxIdleTime = 10000;
    @Value("${jdbc.maxPoolSize}")
    int maxPoolSize = 40;
    @Value("${jdbc.maxStatements}")
    int maxStatements;
    @Value("${jdbc.maxStatementsPerConnection}")
    int maxStatementsPerConnection;
    @Value("${jdbc.numHelperThreads}")
    int numHelperThreads = 3;
    @Value("${jdbc.propertyCycle}")
    int propertyCycle = 600;

    //jpa
    @Value("${jpa.packages}")
    String packages;

    public DataSourceConfig() {
        this.setDriverClass("com.mysql.jdbc.Driver");
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAcquireIncrement() {
        return acquireIncrement;
    }

    public void setAcquireIncrement(int acquireIncrement) {
        this.acquireIncrement = acquireIncrement;
    }

    public int getAcquireRetryAttempts() {
        return acquireRetryAttempts;
    }

    public void setAcquireRetryAttempts(int acquireRetryAttempts) {
        this.acquireRetryAttempts = acquireRetryAttempts;
    }

    public int getAcquireRetryDelay() {
        return acquireRetryDelay;
    }

    public void setAcquireRetryDelay(int acquireRetryDelay) {
        this.acquireRetryDelay = acquireRetryDelay;
    }

    public boolean isAutoCommitOnClose() {
        return autoCommitOnClose;
    }

    public void setAutoCommitOnClose(boolean autoCommitOnClose) {
        this.autoCommitOnClose = autoCommitOnClose;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(int maxStatements) {
        this.maxStatements = maxStatements;
    }

    public int getMaxStatementsPerConnection() {
        return maxStatementsPerConnection;
    }

    public void setMaxStatementsPerConnection(int maxStatementsPerConnection) {
        this.maxStatementsPerConnection = maxStatementsPerConnection;
    }

    public int getNumHelperThreads() {
        return numHelperThreads;
    }

    public void setNumHelperThreads(int numHelperThreads) {
        this.numHelperThreads = numHelperThreads;
    }

    public int getPropertyCycle() {
        return propertyCycle;
    }

    public void setPropertyCycle(int propertyCycle) {
        this.propertyCycle = propertyCycle;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

}
