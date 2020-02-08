package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class TimeslotDaoImpl extends AbstractCrudDaoImpl<TimeslotEntity> implements TimeslotDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM timeslot WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM timeslot";
    private static final String FIND_ALL_BETWEEN_DATES = "SELECT * FROM timeslot WHERE date >= ? AND date < ?";

    private static final String SAVE_ORDER_QUERY = "INSERT INTO \"order\" (date, worker_id, client_id, status_id, service_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_TIMESLOT_ORDER_QUERY = "UPDATE timeslot SET order_id = ? WHERE id = ?";

    private static final String SAVE_QUERY = "INSERT INTO timeslot (date, from_time, to_time, order_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE timeslot SET date = ?, from_time = ?, to_time = ?, order_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM timeslot WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM timeslot";

    public TimeslotDaoImpl(DataSourceConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected TimeslotEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Integer orderId = resultSet.getObject("order_id", Integer.class);
        OrderEntity order;
        if (orderId == null) {
            order = null;
        } else {
            order = OrderEntity.builder()
                    .setId(orderId)
                    .build();
        }

        return TimeslotEntity.builder()
                .setId(resultSet.getInt("id"))
                .setDate(resultSet.getDate("date").toLocalDate())
                .setFromTime(resultSet.getTime("from_time").toLocalTime())
                .setToTime(resultSet.getTime("to_time").toLocalTime())
                .setOrder(order)
                .build();
    }

    @Override
    protected TimeslotEntity applyGeneratedKeysToEntity(TimeslotEntity entity, ResultSet generatedKeys) throws SQLException {
        return TimeslotEntity.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    @Override
    protected void populateInsertStatement(TimeslotEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    public List<TimeslotEntity> findAllBetween(LocalDate from, LocalDate to) {
        return findAllByTwoParams(from, to, FIND_ALL_BETWEEN_DATES, LOCAL_DATE_SETTER);
    }

    @Override
    public void saveOrderAndUpdateTimeslot(TimeslotEntity entity) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            connection.setAutoCommit(false);
            saveOrderAndUpdateTimeslotWithConnection(entity, connection);
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseRuntimeException("Error performing rollback from saveOrderAndUpdateTimeslot", e);
                }
            }
            throw new DatabaseRuntimeException("Error performing saveOrderAndUpdateTimeslot", e);
        }
    }

    private void saveOrderAndUpdateTimeslotWithConnection(TimeslotEntity entity, Connection connection) throws SQLException {
        // todo refactor
        try (PreparedStatement saveOrderStatement = connection.prepareStatement(SAVE_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement updateTimeslotStatement = connection.prepareStatement(UPDATE_TIMESLOT_ORDER_QUERY)) {

            OrderEntity orderEntity = entity.getOrder();
            populateOrderInsertStatement(orderEntity, saveOrderStatement);

            int orderAffectedRows = saveOrderStatement.executeUpdate();
            throwIfNotAffected(orderAffectedRows);

            try (ResultSet generatedKeys = saveOrderStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderEntity = applyGeneratedKeysToOrderEntity(orderEntity, generatedKeys);
                } else {
                    throw new DatabaseRuntimeException("Error saving OrderEntity, no id obtained");
                }
            }

            updateTimeslotStatement.setInt(1, orderEntity.getId());
            updateTimeslotStatement.setInt(2, entity.getId());
            int timeslotAffectedRows = updateTimeslotStatement.executeUpdate();
            throwIfNotAffected(timeslotAffectedRows);
        }
    }

    @Override
    protected void populateUpdateStatement(TimeslotEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(5, entity.getId());
    }

    private void populateNonIdFields(TimeslotEntity entity, PreparedStatement statement) throws SQLException {
        statement.setDate(1, Date.valueOf(entity.getDate()));
        statement.setTime(2, Time.valueOf(entity.getFromTime()));
        statement.setTime(3, Time.valueOf(entity.getToTime()));
        statement.setInt(4, entity.getOrder().getId());
    }

    private OrderEntity applyGeneratedKeysToOrderEntity(OrderEntity entity, ResultSet generatedKeys) throws SQLException {
        return OrderEntity.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    private void populateOrderInsertStatement(OrderEntity entity, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(entity.getDate()));
        statement.setInt(2, entity.getWorker().getId());
        statement.setInt(3, entity.getClient().getId());
        statement.setInt(4, entity.getStatus().getId());
        statement.setInt(5, entity.getService().getId());
    }
}
