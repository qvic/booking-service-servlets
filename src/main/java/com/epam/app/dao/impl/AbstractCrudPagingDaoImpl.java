package com.epam.app.dao.impl;

import com.epam.app.dao.CrudPagingDao;
import com.epam.app.dao.Page;
import com.epam.app.dao.PageProperties;
import com.epam.app.dao.exception.DatabaseRuntimeException;
import com.epam.app.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractCrudPagingDaoImpl<E> extends AbstractCrudDaoImpl<E> implements CrudPagingDao<E> {

    private final String findAllPagingQuery;

    AbstractCrudPagingDaoImpl(DatabaseConnector connector, String findByIdQuery, String insertQuery,
                              String updateQuery, String findAllQuery, String countQuery, String deleteByIdQuery,
                              String findAllPagingQuery) {
        super(connector, findByIdQuery, insertQuery, updateQuery, findAllQuery, countQuery, deleteByIdQuery);
        this.findAllPagingQuery = findAllPagingQuery;
    }

    @Override
    public Page<E> findAll(PageProperties properties) {
        try (PreparedStatement statement = connector.getConnection().prepareStatement(findAllPagingQuery)) {

            statement.setLong(1, properties.getOffset());
            statement.setLong(2, properties.getItemsPerPage());

            try (ResultSet resultSet = statement.executeQuery()) {
                List<E> entities = new ArrayList<>();
                while (resultSet.next()) {
                    E entity = mapResultSetToEntity(resultSet);
                    entities.add(entity);
                }
                long totalCount = count();
                return new Page<>(entities, properties, totalCount);
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error initializing PreparedStatement", e);
        }
    }
}
