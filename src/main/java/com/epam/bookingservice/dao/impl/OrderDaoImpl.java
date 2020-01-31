package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.entity.Order;
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

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM appointment a WHERE a.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM appointment a";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM appointment a OFFSET ? LIMIT ?";

    private static final String SAVE_QUERY = "INSERT INTO appointment (date, worker_id, client_id, timeslot_id, status_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE appointment SET date = ?, worker_id = ?, client_id = ?, timeslot_id = ?, status_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM appointment WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM appointment";

    private final UserDao userDao;
    private final ServiceDao serviceDao;
    private final TimeslotDao timeslotDao;

    public OrderDaoImpl(DatabaseConnector connector, UserDao userDao, ServiceDao serviceDao, TimeslotDao timeslotDao) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
        this.userDao = userDao;
        this.serviceDao = serviceDao;
        this.timeslotDao = timeslotDao;
    }

    @Override
    protected Order mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        User client = userDao.findById(resultSet.getInt("client_id"))
                .orElseThrow(() -> new DatabaseRuntimeException("No client associated with client_id"));
        User worker = userDao.findById(resultSet.getInt("worker_id"))
                .orElseThrow(() -> new DatabaseRuntimeException("No worker associated with worker_id"));
        Timeslot timeslot = timeslotDao.findById(resultSet.getInt("timeslot_id"))
                .orElseThrow(() -> new DatabaseRuntimeException("No timeslot associated with timeslot_id"));
        List<Service> services = serviceDao.findAllByOrderId(resultSet.getInt("order_id"));

        return Order.builder()
                .setId(resultSet.getInt("id"))
                .setClient(client)
                .setWorker(worker)
                .setDate(resultSet.getTimestamp("date").toLocalDateTime())
                .setServices(services)
                .setTimeslot(timeslot)
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
