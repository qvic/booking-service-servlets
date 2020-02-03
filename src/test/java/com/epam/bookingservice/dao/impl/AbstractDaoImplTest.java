package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.utility.DatabaseConnector;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import io.zonky.test.db.postgres.junit.EmbeddedPostgresRules;
import io.zonky.test.db.postgres.junit.SingleInstancePostgresRule;
import org.junit.Rule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDaoImplTest {

    private static final String SQL_SCHEMA_PATH = "sql/schema.sql";
    private static final String SQL_DATA_PATH = "sql/data.sql";
    private static final String SETTINGS_BUNDLE_NAME = "db";

    @Rule
    public SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();
    private DatabaseConnector connector = () -> pg.getEmbeddedPostgres().getPostgresDatabase().getConnection();

    protected DatabaseConnector getConnector() {
        return connector;
    }

    protected void initializeDatabase() {
        try {
            try (Connection connection = connector.getConnection()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(new String(Files.readAllBytes(Paths.get(SQL_SCHEMA_PATH))));
                statement.executeUpdate(new String(Files.readAllBytes(Paths.get(SQL_DATA_PATH))));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Exception during Embedded Postgres test database initialization", e);
        }
    }
}
