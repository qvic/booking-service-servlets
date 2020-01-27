package com.epam.app.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnector {

    private final String url;
    private final String user;
    private final String password;

    public DatabaseConnector(String settingsBundleName) {
        ResourceBundle resource = ResourceBundle.getBundle(settingsBundleName);
        this.url = resource.getString("db.url");
        this.user = resource.getString("db.user");
        this.password = resource.getString("db.password");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
