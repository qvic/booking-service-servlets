package com.bookingservice.dao;

public interface TransactionManager {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();
}
