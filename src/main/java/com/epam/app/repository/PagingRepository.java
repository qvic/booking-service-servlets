package com.epam.app.repository;

public interface PagingRepository<E> extends CrudRepository<E> {

    Page<E> findAll(PageProperties properties);
}
