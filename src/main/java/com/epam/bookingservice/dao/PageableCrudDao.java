package com.epam.bookingservice.dao;

public interface PageableCrudDao<E> extends CrudDao<E> {

    Page<E> findAll(PageProperties properties);
}
