package com.bookingservice.dao;

import com.bookingservice.domain.page.Page;
import com.bookingservice.domain.page.PageProperties;

public interface PageableCrudDao<E> extends CrudDao<E> {

    Page<E> findAll(PageProperties properties);
}
