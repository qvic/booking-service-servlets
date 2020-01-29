package com.epam.app.dao.impl;

import com.epam.app.dao.PageableCrudDao;
import com.epam.app.dao.domain.Page;
import com.epam.app.dao.domain.PageProperties;
import com.epam.app.dao.domain.PageableCrudQuerySet;
import com.epam.app.dao.exception.DatabaseRuntimeException;
import com.epam.app.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

abstract class AbstractPageableCrudDaoImpl<E> extends AbstractCrudDaoImpl<E> implements PageableCrudDao<E> {

    private final PageableCrudQuerySet queries;

    protected AbstractPageableCrudDaoImpl(DatabaseConnector connector, PageableCrudQuerySet queries) {
        super(connector, queries);
        this.queries = queries;
    }

    @Override
    public Page<E> findAll(PageProperties properties) {
        try (PreparedStatement statement = connector.getConnection()
                .prepareStatement(queries.getFindAllPageableQuery())) {

            statement.setLong(1, properties.getOffset());
            statement.setLong(2, properties.getItemsPerPage());

            long totalItemsCount = count();
            List<E> items = getResultList(statement);
            return new Page<>(items, properties, totalItemsCount);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAll", e);
        }
    }
}
