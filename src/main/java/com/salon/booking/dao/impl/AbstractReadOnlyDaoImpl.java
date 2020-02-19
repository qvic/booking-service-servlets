package com.salon.booking.dao.impl;

import com.salon.booking.dao.ReadOnlyDao;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import com.salon.booking.dao.impl.connector.DataSourceConnection;
import com.salon.booking.dao.impl.connector.DataSourceConnector;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class AbstractReadOnlyDaoImpl<E> implements ReadOnlyDao<E> {

    protected static final StatementParameterSetter<String> STRING_SETTER =
            (statement, param, position) -> statement.setString(position, param);

    protected static final StatementParameterSetter<Integer> INT_SETTER =
            (statement, param, position) -> statement.setInt(position, param);

    protected static final StatementParameterSetter<LocalDate> LOCAL_DATE_SETTER =
            (statement, param, position) -> statement.setDate(position, Date.valueOf(param));

    protected final DataSourceConnector connector;
    private final ReadQuerySet queries;

    public AbstractReadOnlyDaoImpl(DataSourceConnector connector, ReadQuerySet queries) {
        this.connector = connector;
        this.queries = queries;
    }

    @Override
    public Optional<E> findById(Integer id) {
        return findByParam(id, queries.getFindByIdQuery(), INT_SETTER);
    }

    @Override
    public List<E> findAll() {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(queries.getFindAllQuery())) {

            return getResultList(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAll", e);
        }
    }

    @Override
    public long count() {
        try (DataSourceConnection connection = connector.getConnection();
             ResultSet resultSet = connection.getOriginal().prepareStatement(queries.getCountQuery()).executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new DatabaseRuntimeException("Error performing count, no results found");
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing count", e);
        }
    }

    protected <P> Optional<E> findByParam(P parameter, String findByParamQuery, StatementParameterSetter<P> paramSetter) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(findByParamQuery)) {

            paramSetter.accept(statement, parameter);

            return getResultOptional(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findByParam", e);
        }
    }

    protected <P> List<E> findAllByParam(P parameter, String findAllByParamQuery, StatementParameterSetter<P> paramSetter) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(findAllByParamQuery)) {

            paramSetter.accept(statement, parameter);

            return getResultList(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAllByParam", e);
        }
    }

    protected <P> List<E> findAllByTwoParams(P parameterA, P parameterB, String findAllByParamQuery, StatementParameterSetter<P> paramSetter) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(findAllByParamQuery)) {

            paramSetter.accept(statement, parameterA, 1);
            paramSetter.accept(statement, parameterB, 2);

            return getResultList(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAllByTwoParams", e);
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

    /**
     * @param resultSet data obtained from database query
     * @return entity generated using ResultSet
     */
    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
