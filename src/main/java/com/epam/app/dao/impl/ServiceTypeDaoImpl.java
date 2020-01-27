package com.epam.app.dao.impl;

import com.epam.app.dao.ServiceTypeDao;
import com.epam.app.domain.ServiceType;
import com.epam.app.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class ServiceTypeDaoImpl extends AbstractCrudPagingDaoImpl<ServiceType> implements ServiceTypeDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM service_type WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO service_type (name, duration_seconds) VALUES (?, ?) RETURNING id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM service_type";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM service_type OFFSET ? LIMIT ?";
    private static final String UPDATE_QUERY = "UPDATE service_type SET name = ?, duration_seconds = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM service_type WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM service_type";

    public ServiceTypeDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, INSERT_QUERY, UPDATE_QUERY,
                FIND_ALL_QUERY, COUNT_QUERY, DELETE_QUERY, FIND_ALL_PAGED_QUERY);
    }

    @Override
    protected ServiceType mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new ServiceType(resultSet.getInt("id"), resultSet.getString("name"),
                Duration.ofSeconds(resultSet.getInt("duration_seconds")));

    }

    @Override
    protected ServiceType applyGeneratedIdToEntity(ServiceType entity, ResultSet resultSet) throws SQLException {
        return new ServiceType(resultSet.getInt(1), entity.getName(), entity.getDuration());
    }

    @Override
    protected void populateInsertStatement(ServiceType entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, Math.toIntExact(entity.getDuration().toSeconds()));
    }

    @Override
    protected void populateUpdateStatement(ServiceType entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, Math.toIntExact(entity.getDuration().toSeconds()));
        statement.setInt(3, entity.getId());
    }
}
