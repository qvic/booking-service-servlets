package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.entity.Order;
import com.epam.bookingservice.entity.OrderStatus;
import com.epam.bookingservice.entity.Service;
import com.epam.bookingservice.entity.Timeslot;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderDaoImpl extends AbstractPageableCrudDaoImpl<Order> implements OrderDao {

    private static final String FIND_BY_ID_QUERY = "SELECT o.id, o.date, o.worker_id, o.client_id, o.timeslot_id, o.status_id, o.service_id FROM \"order\" o WHERE o.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT o.id, o.date, o.worker_id, o.client_id, o.timeslot_id, o.status_id, o.service_id FROM \"order\" o";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT o.id, o.date, o.worker_id, o.client_id, o.timeslot_id, o.status_id, o.service_id FROM \"order\" o OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_CLIENT = "SELECT o.id, o.date, o.worker_id, o.client_id, o.timeslot_id, o.status_id, o.service_id FROM \"order\" o WHERE o.client_id = ?";
    private static final String FIND_ALL_BY_WORKER = "SELECT o.id, o.date, o.worker_id, o.client_id, o.timeslot_id, o.status_id, o.service_id FROM \"order\" o WHERE o.worker_id = ?";

    private static final String SAVE_QUERY = "INSERT INTO \"order\" (date, worker_id, client_id, timeslot_id, status_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE \"order\" SET date = ?, worker_id = ?, client_id = ?, timeslot_id = ?, status_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM \"order\" WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM order";

    public OrderDaoImpl(DatabaseConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public List<Order> findOrdersByClientId(Integer id) {
        return findAllByParam(id, FIND_ALL_BY_CLIENT, INT_SETTER);
    }

    @Override
    public List<Order> findOrdersByWorkerId(Integer id) {
        return findAllByParam(id, FIND_ALL_BY_WORKER, INT_SETTER);
    }

    @Override
    protected Order mapResultSetToEntity(ResultSet resultSet) throws SQLException {

        return Order.builder()
                .setId(resultSet.getInt("id"))
                .setClient(User.builder()
                        .setId(resultSet.getInt("client_id"))
                        .build())
                .setWorker(User.builder()
                        .setId(resultSet.getInt("worker_id"))
                        .build())
                .setDate(resultSet.getTimestamp("date").toLocalDateTime())
                .setService(Service.builder()
                        .setId(resultSet.getInt("service_id"))
                        .build())
                .setTimeslot(Timeslot.builder()
                        .setId(resultSet.getInt("timeslot_id"))
                        .build())
                .setStatus(OrderStatus.getById(resultSet.getInt("status_id")))
                .build();
    }

    @Override
    protected Order applyGeneratedKeysToEntity(Order entity, ResultSet generatedKeys) throws SQLException {
        return Order.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    @Override
    protected void populateInsertStatement(Order entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(Order entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(6, entity.getId());
    }

    private void populateNonIdFields(Order entity, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(entity.getDate()));
        statement.setInt(2, entity.getWorker().getId());
        statement.setInt(3, entity.getClient().getId());
        statement.setInt(4, entity.getTimeslot().getId());
        statement.setInt(5, entity.getStatus().getId());
    }
}
