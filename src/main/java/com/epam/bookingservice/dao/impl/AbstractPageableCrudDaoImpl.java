package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.domain.Page;
import com.epam.bookingservice.domain.PageProperties;
import com.epam.bookingservice.dao.PageableCrudDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

abstract class AbstractPageableCrudDaoImpl<E> extends AbstractCrudDaoImpl<E> implements PageableCrudDao<E> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractPageableCrudDaoImpl.class);

    private final PageableCrudQuerySet queries;

    protected AbstractPageableCrudDaoImpl(DataSourceConnector connector, PageableCrudQuerySet queries) {
        super(connector, queries);
        this.queries = queries;
    }

    @Override
    public Page<E> findAll(PageProperties properties) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(queries.getFindAllPageableQuery())) {

            statement.setLong(1, properties.getOffset());
            statement.setLong(2, properties.getItemsPerPage());

            long totalItemsCount = count();
            List<E> items = getResultList(statement);
            return new Page<>(items, properties, totalItemsCount);
        } catch (SQLException e) {
            LOGGER.error("Error performing findAll with [" + properties + "]", e);
            throw new DatabaseRuntimeException("Error performing findAll", e);
        }
    }
}
