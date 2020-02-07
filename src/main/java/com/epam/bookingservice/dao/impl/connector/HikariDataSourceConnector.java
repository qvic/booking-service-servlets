package com.epam.bookingservice.dao.impl.connector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HikariDataSourceConnector implements DataSourceConnector {

    private final HikariDataSource dataSource;

    public HikariDataSourceConnector(String settingsBundleName) {
        ResourceBundle resource = ResourceBundle.getBundle(settingsBundleName);
        HikariConfig hikariConfig = getHikariConfigByResource(resource);
        dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
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
