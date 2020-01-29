package com.epam.bookingservice.utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HikariDatabaseConnector {

    private final HikariDataSource dataSource;

    public HikariDatabaseConnector(String settingsBundleName) {
        ResourceBundle resource = ResourceBundle.getBundle(settingsBundleName);
        HikariConfig hikariConfig = getHikariConfigByResource(resource);
        dataSource = new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static HikariConfig getHikariConfigByResource(ResourceBundle resource) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(resource.getString("db.url"));
        config.setUsername(resource.getString("db.username"));
        config.setPassword(resource.getString("db.password"));
        config.setConnectionTimeout(Long.parseLong(resource.getString("db.hikari.connectionTimeout")));
        return config;
    }
}
