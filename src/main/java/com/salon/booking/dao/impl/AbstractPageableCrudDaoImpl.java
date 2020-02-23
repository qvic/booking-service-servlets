package com.salon.booking.dao.impl;

import com.salon.booking.dao.PageableCrudDao;
import com.salon.booking.dao.impl.connector.DataSourceConnection;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

abstract class AbstractPageableCrudDaoImpl<E> extends AbstractCrudDaoImpl<E> implements PageableCrudDao<E> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractPageableCrudDaoImpl.class);

    private final PageableCrudQuerySet queries;

    AbstractPageableCrudDaoImpl(DataSourceConnector connector, PageableCrudQuerySet queries) {
        super(connector, queries);
        this.queries = queries;
    }

    @Override
    public Page<E> findAll(PageProperties properties) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(queries.getFindAllPageableQuery())) {

            statement.setLong(1, properties.getOffset());
            statement.setLong(2, properties.getItemsPerPage());

            return getResultPage(statement, properties, count());
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseRuntimeException("Error performing findAll", e);
        }
    }

    <P> Page<E> findPageByParam(P parameter, String findByParamQuery, String countByParamQuery, StatementParameterSetter<P> paramSetter, PageProperties properties) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(findByParamQuery)) {

            paramSetter.accept(statement, parameter, 1);
            statement.setLong(2, properties.getOffset());
            statement.setLong(3, properties.getItemsPerPage());

            long totalItemsCount = countByParam(parameter, countByParamQuery, paramSetter);
            return getResultPage(statement, properties, totalItemsCount);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseRuntimeException("Error performing findByParam", e);
        }
    }

    private Page<E> getResultPage(PreparedStatement statement, PageProperties properties, long totalItemsCount) throws SQLException {
        List<E> items = getResultList(statement);
        return new Page<>(items, properties, totalItemsCount);
    }
}
