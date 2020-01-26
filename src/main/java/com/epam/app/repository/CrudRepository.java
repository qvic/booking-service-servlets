package com.epam.app.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E> {

    E save(E entity);

    Optional<E> findById(Integer id);

    List<E> findAll();

    E update(E entity);

    void deleteById(Integer id);

    long count();
}
