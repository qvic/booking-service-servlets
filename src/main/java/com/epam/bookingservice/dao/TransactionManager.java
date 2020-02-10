package com.epam.bookingservice.dao;

public interface TransactionManager {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();
}
