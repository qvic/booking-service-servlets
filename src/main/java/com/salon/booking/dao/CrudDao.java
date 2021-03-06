package com.salon.booking.dao;

public interface CrudDao<E> extends ReadOnlyDao<E> {

    E save(E entity);

    void update(E entity);

    void deleteById(Integer id);
}
