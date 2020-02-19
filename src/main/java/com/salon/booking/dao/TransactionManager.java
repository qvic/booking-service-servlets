package com.salon.booking.dao;

public interface TransactionManager {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();
}
