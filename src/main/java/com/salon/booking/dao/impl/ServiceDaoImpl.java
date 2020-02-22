package com.salon.booking.dao.impl;

import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.entity.ServiceEntity;
import com.salon.booking.dao.ServiceDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceDaoImpl extends AbstractCrudDaoImpl<ServiceEntity> implements ServiceDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM service WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM service";
    private static final String COUNT_QUERY = "SELECT count(*) FROM service";

    private static final String INSERT_QUERY = "INSERT INTO service (name, duration_minutes, price) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE service SET name = ?, duration_timeslots = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM service WHERE id = ?";

    public ServiceDaoImpl(DataSourceConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, INSERT_QUERY,
                UPDATE_QUERY, DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected ServiceEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return ServiceEntity.builder().setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setDurationMinutes(resultSet.getInt("duration_minutes"))
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
        statement.setInt(4, entity.getId());
    }

    private void populateNonIdFields(ServiceEntity entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getDurationMinutes());
        statement.setInt(3, entity.getPrice());
    }
}
