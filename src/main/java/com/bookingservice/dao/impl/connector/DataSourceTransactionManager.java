package com.bookingservice.dao.impl.connector;

import com.bookingservice.dao.TransactionManager;
import com.bookingservice.dao.exception.DatabaseRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceTransactionManager implements TransactionManager {

    private static final Logger LOGGER = LogManager.getLogger(DataSourceTransactionManager.class);

    private final ThreadLocal<Connection> currentConnection;
    private final ThreadLocal<Boolean> inTransactionContext;

    public DataSourceTransactionManager() {
        this.currentConnection = new ThreadLocal<>();
        this.inTransactionContext = ThreadLocal.withInitial(() -> false);
    }

    @Override
    public void beginTransaction() {
        inTransactionContext.set(true);
        LOGGER.info("In transaction");
    }

    @Override
    public void commitTransaction() {
        Connection connection = currentConnection.get();
        if (connection == null) {
            throw new DatabaseRuntimeException("commitTransaction() can be called only after beginTransaction()");
        }

        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Can't commit transaction", e);
        } finally {
            inTransactionContext.remove();
            currentConnection.remove();
        }
        LOGGER.info("Committed successfully");
    }

    @Override
    public void rollbackTransaction() {
        Connection connection = currentConnection.get();
        if (connection == null) {
            throw new DatabaseRuntimeException("rollbackTransaction() can be called only after commitTransaction()");
        }

        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Can't rollback transaction", e);
        }
        LOGGER.info("Rolled back successfully");
    }

    void setConnection(Connection connection) {
        try {
            connection.setAutoCommit(false);
            currentConnection.set(connection);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Can't setAutoCommit(false)", e);
        }
    }

    boolean isInTransaction() {
        return inTransactionContext.get();
    }

    boolean hasConnection() {
        return currentConnection.get() != null;
    }

    DataSourceConnection getConnection() {
        Connection connection = currentConnection.get();
        return new DataSourceConnection(connection, true);
    }
}
