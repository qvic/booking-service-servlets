package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import com.epam.bookingservice.entity.ServiceEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceDaoImpl extends AbstractCrudDaoImpl<ServiceEntity> implements ServiceDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM service WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM service";

    private static final String INSERT_QUERY = "INSERT INTO service (name, duration_timeslots, price, workspaces) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE service SET name = ?, duration_timeslots = ?, price = ?, workspaces = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM service WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM service";

    public ServiceDaoImpl(DataSourceConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, INSERT_QUERY,
                UPDATE_QUERY, DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected ServiceEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return ServiceEntity.builder().setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setDurationInTimeslots(resultSet.getInt("duration_timeslots"))
                .setWorkspaces(resultSet.getInt("workspaces"))
                .setPrice(resultSet.getInt("price"))
                .build();

    }

    @Override
    protected ServiceEntity applyGeneratedKeysToEntity(ServiceEntity entity, ResultSet generatedKeys) throws SQLException {
        return ServiceEntity.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    @Override
    protected void populateInsertStatement(ServiceEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(ServiceEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(5, entity.getId());
    }

    private void populateNonIdFields(ServiceEntity entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getDurationInTimeslots());
        statement.setInt(3, entity.getPrice());
        statement.setInt(4, entity.getWorkspaces());
    }
}
