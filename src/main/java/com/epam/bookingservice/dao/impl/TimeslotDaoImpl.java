package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnection;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class TimeslotDaoImpl extends AbstractCrudDaoImpl<TimeslotEntity> implements TimeslotDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM timeslot WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM timeslot";
    private static final String FIND_ALL_BETWEEN_DATES = "SELECT * FROM timeslot WHERE date >= ? AND date < ?";

    private static final String UPDATE_TIMESLOT_ORDER_ID_QUERY = "UPDATE timeslot SET order_id = ? WHERE id = ?";

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
    public void updateOrder(TimeslotEntity entity) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(UPDATE_TIMESLOT_ORDER_ID_QUERY)) {

            statement.setInt(1, entity.getOrder().getId());
            statement.setInt(2, entity.getId());

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing updateOrder", e);
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
}
