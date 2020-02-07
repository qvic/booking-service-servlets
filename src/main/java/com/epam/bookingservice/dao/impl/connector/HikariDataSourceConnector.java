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
        config.setConnectionTimeout(Long.parseLong(resource.getString("db.hikari.connectionTimeout")));
        config.setMaximumPoolSize(Integer.parseInt(resource.getString("db.hikari.maxPoolSize")));
        config.addDataSourceProperty("closeMethod", "close");
        config.addDataSourceProperty("cachePrepStmts", resource.getString("db.hikari.cachePrepStmts"));
        config.addDataSourceProperty("prepStmtCacheSize", resource.getString("db.hikari.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", resource.getString("db.hikari.prepStmtCacheSqlLimit"));
        return config;
    }
}
