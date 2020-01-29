package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.CrudDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.utility.DatabaseConnector;
import com.epam.bookingservice.utility.SimpleDatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class AbstractCrudDaoImpl<E> implements CrudDao<E> {

    private static final Logger logger = LogManager.getLogger(AbstractCrudDaoImpl.class);

    protected static final StatementParamSetter<String> STRING_SETTER =
            (PreparedStatement statement, String param) -> statement.setString(1, param);

    protected static final StatementParamSetter<Integer> INT_SETTER =
            (PreparedStatement statement, Integer param) -> statement.setInt(1, param);

    protected final DatabaseConnector connector;

    private CrudQuerySet queries;

    protected AbstractCrudDaoImpl(DatabaseConnector connector, CrudQuerySet queries) {
        this.connector = connector;
        this.queries = queries;
    }

    @Override
    public List<E> findAll() {
        try (PreparedStatement statement = connector.getConnection()
                .prepareStatement(queries.getFindAllQuery())) {

            return getResultList(statement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findAll", e);
        }
    }

    @Override
    public void update(E entity) {
        try (PreparedStatement statement = connector.getConnection()
                .prepareStatement(queries.getUpdateQuery())) {

            populateUpdateStatement(entity, statement);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing update", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement statement = connector.getConnection()
                .prepareStatement(queries.getDeleteByIdQuery())) {

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseRuntimeException("Error performing deleteById, no rows affected");
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing deleteById", e);
        }
    }

    @Override
    public long count() {
        try (ResultSet resultSet = connector.getConnection()
                .prepareStatement(queries.getCountQuery())
                .executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new DatabaseRuntimeException("Error performing count, no results found");
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing count", e);
        }
    }

    @Override
    public E save(E entity) {
        try (PreparedStatement statement = connector.getConnection()
                .prepareStatement(queries.getSaveQuery(), Statement.RETURN_GENERATED_KEYS)) {

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

    protected <P> Optional<E> findByParam(P parameter, String findByParamQuery, StatementParamSetter<P> paramSetter) {
        try (PreparedStatement preparedStatement = connector.getConnection()
                .prepareStatement(findByParamQuery)) {

            paramSetter.accept(preparedStatement, parameter);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findByParam", e);
        }

        return Optional.empty();
    }

    protected <P> List<E> findAllByParam(P parameter, String findAllByParamQuery, StatementParamSetter<P> paramSetter) {
        try (PreparedStatement preparedStatement = connector.getConnection()
                .prepareStatement(findAllByParamQuery)) {

            paramSetter.accept(preparedStatement, parameter);

            return getResultList(preparedStatement);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing findByParam", e);
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