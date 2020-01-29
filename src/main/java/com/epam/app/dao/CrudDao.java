package com.epam.app.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E> {

    Optional<E> findById(Integer id);

    List<E> findAll();

    E save(E entity);

    void update(E entity);

    void deleteById(Integer id);

    long count();
}
