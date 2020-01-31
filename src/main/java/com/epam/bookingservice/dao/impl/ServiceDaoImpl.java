package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.entity.Service;
import com.epam.bookingservice.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ServiceDaoImpl extends AbstractCrudDaoImpl<Service> implements ServiceDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM service WHERE id=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM service";
    private static final String FIND_SERVICES_BY_ORDER = "SELECT s.id, s.name, s.duration_timeslots, s.price, s.workspaces FROM service s INNER JOIN order_service os on s.id = os.service_id WHERE os.order_id = ?";

    private static final String INSERT_QUERY = "INSERT INTO service (name, duration_timeslots, price) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE service SET name = ?, duration_seconds = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM service WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM service";

    public ServiceDaoImpl(DatabaseConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, INSERT_QUERY,
                UPDATE_QUERY, DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    public List<Service> findAllByOrderId(Integer orderId) {
        return findAllByParam(orderId, FIND_SERVICES_BY_ORDER, INT_SETTER);
    }

    @Override
    protected Service mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Service.builder().setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setDurationInTimeslots(resultSet.getInt("duration_timeslots"))
                .setWorkspaces(resultSet.getInt("workplaces"))
                .setPrice(resultSet.getInt("price"))
                .build();

    }

    @Override
    protected Service applyGeneratedKeysToEntity(Service entity, ResultSet generatedKeys) throws SQLException {
        return Service.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    @Override
    protected void populateInsertStatement(Service entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(Service entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(5, entity.getId());
    }

    private void populateNonIdFields(Service entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getDurationInTimeslots());
        statement.setInt(3, entity.getPrice());
        statement.setInt(4, entity.getWorkspaces());
    }
}
