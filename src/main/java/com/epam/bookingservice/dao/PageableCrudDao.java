package com.epam.bookingservice.dao;

import com.epam.bookingservice.domain.page.Page;
import com.epam.bookingservice.domain.page.PageProperties;

public interface PageableCrudDao<E> extends CrudDao<E> {

    Page<E> findAll(PageProperties properties);
}
