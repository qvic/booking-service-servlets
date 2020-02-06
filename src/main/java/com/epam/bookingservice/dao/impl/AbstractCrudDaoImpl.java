package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.CrudDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class AbstractCrudDaoImpl<E> implements CrudDao<E> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDaoImpl.class);

    protected static final StatementParamSetter<String> STRING_SETTER =
            (statement, param, position) -> statement.setString(position, param);

    protected static final StatementParamSetter<Integer> INT_SETTER =
            (statement, param, position) -> statement.setInt(position, param);

    protected static final StatementParamSetter<LocalDate> LOCAL_DATE_SETTER =
            (statement, param, position) -> statement.setDate(position, Date.valueOf(param));

    protected final DataSourceConnector connector;

    private CrudQuerySet queries;

    protected AbstractCrudDaoImpl(DataSourceConnector connector, CrudQuerySet queries) {
        this.connector = connector;
        this.queries = queries;
    }

    @Override
    public List<E> findAll() {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(queries.getFindAllQuery())) {

            return getResultList(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAll", e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(queries.getUpdateQuery())) {

            populateUpdateStatement(entity, statement);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing update", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(queries.getDeleteByIdQuery())) {

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing deleteById", e);
        }
    }

    @Override
    public long count() {
        try (Connection connection = connector.getConnection();
             ResultSet resultSet = connection.prepareStatement(queries.getCountQuery()).executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                LOGGER.error("Error performing count, no results found");
                throw new DatabaseRuntimeException("Error performing count, no results found");
            }
        } catch (SQLException e) {
            LOGGER.error("Error performing count", e);
            throw new DatabaseRuntimeException("Error performing count", e);
        }
    }

    @Override
    public E save(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(queries.getSaveQuery(), Statement.RETURN_GENERATED_KEYS)) {

            populateInsertStatement(entity, statement);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return applyGeneratedKeysToEntity(entity, generatedKeys);
                } else {
                    throw new DatabaseRuntimeException("Error performing save, no id obtained");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing save", e);
        }
    }

    @Override
    public Optional<E> findById(Integer id) {
        return findByParam(id, queries.getFindByIdQuery(), INT_SETTER);
    }

    private void throwIfNotAffected(int affectedRows) {
        if (affectedRows == 0) {
            throw new DatabaseRuntimeException("No rows affected");
        }
    }

    protected List<E> getResultList(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            List<E> entities = new ArrayList<>();
            while (resultSet.next()) {
                E entity = mapResultSetToEntity(resultSet);
                entities.add(entity);
            }
            return entities;
        }
    }

    protected Optional<E> getResultOptional(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(mapResultSetToEntity(resultSet));
            }
        }
        return Optional.empty();
    }

    protected <P> Optional<E> findByParam(P parameter, String findByParamQuery, StatementParamSetter<P> paramSetter) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByParamQuery)) {

            paramSetter.accept(statement, parameter);

            return getResultOptional(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findByParam", e);
        }
    }

    protected <P> List<E> findAllByParam(P parameter, String findAllByParamQuery, StatementParamSetter<P> paramSetter) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllByParamQuery)) {

            paramSetter.accept(statement, parameter);

            return getResultList(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAllByParam", e);
        }
    }

    protected <P> List<E> findAllByTwoParams(P parameterA, P parameterB, String findAllByParamQuery, StatementParamSetter<P> paramSetter) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllByParamQuery)) {

            paramSetter.accept(statement, parameterA, 1);
            paramSetter.accept(statement, parameterB, 2);

            return getResultList(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAllByTwoParams", e);
        }
    }

    /**
     * @param resultSet data obtained from database query
     * @return entity generated using ResultSet
     */
    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

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