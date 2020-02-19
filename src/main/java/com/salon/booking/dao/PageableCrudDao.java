package com.salon.booking.dao;

import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;

public interface PageableCrudDao<E> extends CrudDao<E> {

    Page<E> findAll(PageProperties properties);
}
