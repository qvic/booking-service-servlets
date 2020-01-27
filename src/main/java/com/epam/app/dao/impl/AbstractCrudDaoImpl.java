package com.epam.app.dao.impl;

import com.epam.app.dao.CrudDao;
import com.epam.app.dao.exception.DatabaseRuntimeException;
import com.epam.app.domain.User;
import com.epam.app.utility.DatabaseConnector;
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

    final DatabaseConnector connector;

    private final String findById;
    private final String insertQuery;
    private final String updateQuery;
    private final String findAllQuery;
    private final String countQuery;
    private String deleteByIdQuery;

    AbstractCrudDaoImpl(DatabaseConnector connector, String findByIdQuery, String insertQuery,
                        String updateQuery, String findAllQuery, String countQuery, String deleteByIdQuery) {
        this.connector = connector;

        this.findById = findByIdQuery;
        this.insertQuery = insertQuery;
        this.updateQuery = updateQuery;
        this.findAllQuery = findAllQuery;
        this.countQuery = countQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public List<E> findAll() {
        try (PreparedStatement statement = connector.getConnection().prepareStatement(findAllQuery)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<E> entities = new ArrayList<>();
                while (resultSet.next()) {
                    E entity = mapResultSetToEntity(resultSet);
                    entities.add(entity);
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error initializing PreparedStatement", e);
        }
    }

    @Override
    public void update(E entity) {
        try (PreparedStatement statement = connector.getConnection().prepareStatement(updateQuery)) {

            populateUpdateStatement(entity, statement);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseRuntimeException("Update failed, no rows affected");
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error initializing PreparedStatement", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement statement = connector.getConnection().prepareStatement(deleteByIdQuery)) {

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseRuntimeException("Deletion failed, no rows affected");
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error initializing PreparedStatement", e);
        }
    }

    @Override
    public long count() {
        try (PreparedStatement statement = connector.getConnection().prepareStatement(countQuery)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error initializing PreparedStatement", e);
        }

        throw new DatabaseRuntimeException("Count failed, no results found");
    }

    @Override
    public E save(E entity) {
        try (PreparedStatement statement =
                     connector.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            populateInsertStatement(entity, statement);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseRuntimeException("Insertion failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return applyGeneratedIdToEntity(entity, generatedKeys);
                } else {
                    throw new DatabaseRuntimeException("Insertion failed, no id obtained");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error initializing PreparedStatement", e);
        }
    }

    @Override
    public Optional<E> findById(Integer id) {
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(findById)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error initializing PreparedStatement", e);
        }

        return Optional.empty();
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract E applyGeneratedIdToEntity(E entity, ResultSet resultSet) throws SQLException;

    protected abstract void populateInsertStatement(E entity, PreparedStatement statement) throws SQLException;

    protected abstract void populateUpdateStatement(E entity, PreparedStatement statement) throws SQLException;
}