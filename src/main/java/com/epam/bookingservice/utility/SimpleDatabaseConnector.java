package com.epam.bookingservice.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SimpleDatabaseConnector implements DatabaseConnector {

    private final String url;
    private final String user;
    private final String password;

    public SimpleDatabaseConnector(String settingsBundleName) {
        ResourceBundle resource = ResourceBundle.getBundle(settingsBundleName);
        this.url = resource.getString("db.url");
        this.user = resource.getString("db.username");
        this.password = resource.getString("db.password");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}