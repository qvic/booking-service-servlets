package com.epam.bookingservice.dao.impl.connector;

import java.sql.Connection;

public interface TransactionManager {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();

    void assignConnection(Connection connection);

    boolean isInTransaction();

    boolean hasConnection();

    ConnectionWrapper getConnection();
}
