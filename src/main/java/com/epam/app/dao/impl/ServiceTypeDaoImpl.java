package com.epam.app.dao.impl;

import com.epam.app.dao.ServiceTypeDao;
import com.epam.app.dao.domain.CrudQuerySet;
import com.epam.app.entity.ServiceType;
import com.epam.app.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class ServiceTypeDaoImpl extends AbstractCrudDaoImpl<ServiceType> implements ServiceTypeDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM service WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO service (name, duration_seconds) VALUES (?, ?) RETURNING id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM service";
    private static final String UPDATE_QUERY = "UPDATE service SET name = ?, duration_seconds = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM service WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM service";

    public ServiceTypeDaoImpl(DatabaseConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, INSERT_QUERY,
                UPDATE_QUERY, DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected ServiceType mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return ServiceType.builder().setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setDuration(Duration.ofSeconds(resultSet.getInt("duration_seconds")))
                .setAvailableWorkplaces(resultSet.getInt("available_workplaces"))
                .build();

    }

    @Override
    protected ServiceType applyGeneratedKeysToEntity(ServiceType entity, ResultSet generatedKeys) throws SQLException {
        entity.setId(generatedKeys.getInt("id"));
        return entity;
    }

    @Override
    protected void populateInsertStatement(ServiceType entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(ServiceType entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(3, entity.getId());
    }

    private void populateNonIdFields(ServiceType entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, Math.toIntExact(entity.getDuration().toSeconds()));
    }
}
