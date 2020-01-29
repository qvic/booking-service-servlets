package com.epam.app.dao;

import com.epam.app.dao.domain.Page;
import com.epam.app.dao.domain.PageProperties;

public interface PageableCrudDao<E> extends CrudDao<E> {

    Page<E> findAll(PageProperties properties);
}
