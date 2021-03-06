package com.salon.booking.dao.impl;

import com.salon.booking.dao.CrudDao;
import com.salon.booking.dao.impl.connector.DataSourceConnection;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class AbstractCrudDaoImpl<E> extends AbstractReadOnlyDaoImpl<E> implements CrudDao<E> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDaoImpl.class);

    private CrudQuerySet queries;

    AbstractCrudDaoImpl(DataSourceConnector connector, CrudQuerySet queries) {
        super(connector, queries);
        this.queries = queries;
    }

    @Override
    public void update(E entity) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(queries.getUpdateQuery())) {

            populateUpdateStatement(entity, statement);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseRuntimeException("Error performing update", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(queries.getDeleteByIdQuery())) {

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseRuntimeException("Error performing deleteById", e);
        }
    }

    @Override
    public E save(E entity) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(queries.getSaveQuery(), Statement.RETURN_GENERATED_KEYS)) {

            populateInsertStatement(entity, statement);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return applyGeneratedKeysToEntity(entity, generatedKeys);
                } else {
                    LOGGER.error("No id obtained");
                    throw new DatabaseRuntimeException("Error performing save, no id obtained");
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseRuntimeException("Error performing save", e);
        }
    }

    void throwIfNotAffected(int affectedRows) {
        if (affectedRows == 0) {
            LOGGER.error("No rows affected");
            throw new DatabaseRuntimeException("No rows affected");
        }
    }

    /**
     * @param entity        entity to be saved
     * @param generatedKeys keys generated by database and returned after insert
     * @return saved entity with generated keys set up
     */
    protected abstract E applyGeneratedKeysToEntity(E entity, ResultSet generatedKeys) throws SQLException;

    /**
     * @param entity    to get fields
     * @param statement to set parameters for INSERT query
     */
    protected abstract void populateInsertStatement(E entity, PreparedStatement statement) throws SQLException;

    /**
     * @param entity    to get fields
     * @param statement to set parameters for UPDATE query
     */
    protected abstract void populateUpdateStatement(E entity, PreparedStatement statement) throws SQLException;
}