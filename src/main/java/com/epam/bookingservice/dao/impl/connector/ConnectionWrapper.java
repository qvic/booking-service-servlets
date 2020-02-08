package com.epam.bookingservice.dao.impl.connector;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionWrapper implements AutoCloseable {

    private final Connection connection;
    private final boolean inTransaction;

    public ConnectionWrapper(Connection connection, boolean inTransaction) {
        this.connection = connection;
        this.inTransaction = inTransaction;
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
