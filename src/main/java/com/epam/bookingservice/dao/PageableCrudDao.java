package com.epam.bookingservice.dao;

import com.epam.bookingservice.domain.Page;
import com.epam.bookingservice.domain.PageProperties;

public interface PageableCrudDao<E> extends CrudDao<E> {

    Page<E> findAll(PageProperties properties);
}
