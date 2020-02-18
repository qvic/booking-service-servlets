package com.bookingservice.dao.impl;

import com.bookingservice.dao.PageableCrudDao;
import com.bookingservice.dao.impl.connector.DataSourceConnection;
import com.bookingservice.dao.impl.connector.DataSourceConnector;
import com.bookingservice.domain.page.Page;
import com.bookingservice.domain.page.PageProperties;
import com.bookingservice.dao.exception.DatabaseRuntimeException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

abstract class AbstractPageableCrudDaoImpl<E> extends AbstractCrudDaoImpl<E> implements PageableCrudDao<E> {

    private final PageableCrudQuerySet queries;

    protected AbstractPageableCrudDaoImpl(DataSourceConnector connector, PageableCrudQuerySet queries) {
        super(connector, queries);
        this.queries = queries;
    }

    @Override
    public Page<E> findAll(PageProperties properties) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(queries.getFindAllPageableQuery())) {

            statement.setLong(1, properties.getOffset());
            statement.setLong(2, properties.getItemsPerPage());

            return getResultPage(statement, properties);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAll", e);
        }
    }

    protected <P> Page<E> findPageByParam(P parameter, String findByParamQuery, StatementParameterSetter<P> paramSetter, PageProperties properties) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(findByParamQuery)) {

            paramSetter.accept(statement, parameter, 1);
            statement.setLong(2, properties.getOffset());
            statement.setLong(3, properties.getItemsPerPage());

            return getResultPage(statement, properties);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findByParam", e);
        }
    }

    protected Page<E> getResultPage(PreparedStatement statement, PageProperties properties) throws SQLException {
        long totalItemsCount = count();
        List<E> items = getResultList(statement);
        return new Page<>(items, properties, totalItemsCount);
    }
}
