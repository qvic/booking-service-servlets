package com.salon.booking.dao.impl.connector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class HikariDataSourceConnector implements DataSourceConnector {

    private final HikariDataSource dataSource;
    private final DataSourceTransactionManager transactionManager;

    public HikariDataSourceConnector(String settingsBundleName, DataSourceTransactionManager transactionManager) {
        this.dataSource = getConfiguredHikariDataSource(settingsBundleName);
        this.transactionManager = transactionManager;
    }

    @Override
    public DataSourceConnection getConnection() throws SQLException {
        if (transactionManager.isInTransaction()) {
            if (!transactionManager.hasConnection()) {
                transactionManager.setConnection(dataSource.getConnection());
            }
            return transactionManager.getConnection();
        }

        return new DataSourceConnection(dataSource.getConnection());
    }

    private static HikariDataSource getConfiguredHikariDataSource(String settingsBundleName) {
        ResourceBundle resource = ResourceBundle.getBundle(settingsBundleName);
        HikariConfig hikariConfig = getHikariConfigByResource(resource);
        return new HikariDataSource(hikariConfig);
    }

    private static HikariConfig getHikariConfigByResource(ResourceBundle resource) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(resource.getString("db.url"));
        config.setDriverClassName(resource.getString("db.hikari.driverClassName"));
        config.setUsername(resource.getString("db.username"));
        config.setPassword(resource.getString("db.password"));
        config.setConnectionTimeout(getLongResourceProperty("db.hikari.connectionTimeout", resource));
        config.setMaximumPoolSize(getIntResourceProperty("db.hikari.maxPoolSize", resource));
        config.addDataSourceProperty("cachePrepStmts", resource.getString("db.hikari.cachePrepStmts"));
        config.addDataSourceProperty("prepStmtCacheSize", resource.getString("db.hikari.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", resource.getString("db.hikari.prepStmtCacheSqlLimit"));
        return config;
    }

    private static long getLongResourceProperty(String key, ResourceBundle resource) {
        return Long.parseLong(resource.getString(key));
    }

    private static int getIntResourceProperty(String key, ResourceBundle resource) {
        return Integer.parseInt(resource.getString(key));
    }
}
