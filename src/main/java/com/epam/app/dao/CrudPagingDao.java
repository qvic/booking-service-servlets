package com.epam.app.dao;

public interface CrudPagingDao<E> extends CrudDao<E> {

    Page<E> findAll(PageProperties properties);
}
