package com.epam.bookingservice.dao;

import java.util.List;
import java.util.Optional;

public interface ReadOnlyDao<E> {

    Optional<E> findById(Integer id);

    List<E> findAll();

    long count();
}
