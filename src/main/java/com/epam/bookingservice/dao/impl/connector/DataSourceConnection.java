package com.epam.bookingservice.dao.impl.connector;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceConnection implements AutoCloseable {

    private final Connection connection;
    private final boolean inTransaction;

    public DataSourceConnection(Connection connection, boolean inTransaction) {
        this.connection = connection;
        this.inTransaction = inTransaction;
    }

    public DataSourceConnection(Connection connection) {
        this(connection, false);
    }

    public Connection getOriginal() {
        return connection;
    }

    @Override
    public void close() throws SQLException {
        if (!inTransaction) {
            closeOriginalConnection();
        }
    }

    private void closeOriginalConnection() throws SQLException {
        connection.close();
    }
}
