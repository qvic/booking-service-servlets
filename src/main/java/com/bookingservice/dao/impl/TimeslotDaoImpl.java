package com.bookingservice.dao.impl;

import com.bookingservice.dao.TimeslotDao;
import com.bookingservice.dao.impl.connector.DataSourceConnection;
import com.bookingservice.dao.impl.connector.DataSourceConnector;
import com.bookingservice.entity.DurationEntity;
import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.TimeslotEntity;
import com.bookingservice.dao.exception.DatabaseRuntimeException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class TimeslotDaoImpl extends AbstractCrudDaoImpl<TimeslotEntity> implements TimeslotDao {

    private static final String FIND_ALL_QUERY = "SELECT t.*, d.minutes FROM timeslot t INNER JOIN duration d ON t.duration_id = d.id";
    private static final String FIND_BY_ID_QUERY = "SELECT t.*, d.minutes FROM timeslot t INNER JOIN duration d ON t.duration_id = d.id WHERE t.id = ?";
    private static final String FIND_ALL_BETWEEN_DATES = "SELECT t.*, d.minutes FROM timeslot t INNER JOIN duration d ON t.duration_id = d.id " +
            "WHERE t.date >= ? AND t.date < ? ORDER BY t.date ASC, t.from_time ASC";
    private static final String FIND_CONSECUTIVE_FREE_TIMESLOTS = ""; // todo oooo
    private static final String COUNT_QUERY = "SELECT count(*) FROM timeslot";

    private static final String SAVE_QUERY = "INSERT INTO timeslot (date, from_time, order_id, duration_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE timeslot SET date = ?, from_time = ?, order_id = ?, duration_id = ? WHERE id = ?";
    private static final String UPDATE_TIMESLOT_ORDER_ID_QUERY = "UPDATE timeslot SET order_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM timeslot WHERE id = ?";

    public TimeslotDaoImpl(DataSourceConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected TimeslotEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return TimeslotEntity.builder()
                .setId(resultSet.getInt("id"))
                .setDate(resultSet.getDate("date").toLocalDate())
                .setFromTime(resultSet.getTime("from_time").toLocalTime())
                .setDuration(mapDuration(resultSet))
                .setOrder(mapOrder(resultSet))
                .build();
    }

    private DurationEntity mapDuration(ResultSet resultSet) throws SQLException {
        return new DurationEntity(
                resultSet.getInt("duration_id"),
                resultSet.getInt("minutes"));
    }

    private OrderEntity mapOrder(ResultSet resultSet) throws SQLException {
        Integer orderId = resultSet.getObject("order_id", Integer.class);
        return orderId == null ? null : OrderEntity.builder()
                .setId(orderId)
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
    public void updateOrderId(Integer timeslotId, Integer orderId) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(UPDATE_TIMESLOT_ORDER_ID_QUERY)) {

            statement.setInt(1, orderId);
            statement.setInt(2, timeslotId);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing updateOrderId", e);
        }
    }

    @Override
    public List<TimeslotEntity> findConsecutiveFreeTimeslots(Integer timeslotId) {
        return findAllByParam(timeslotId, FIND_CONSECUTIVE_FREE_TIMESLOTS, INT_SETTER);
    }

    @Override
    protected void populateUpdateStatement(TimeslotEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(5, entity.getId());
    }

    private void populateNonIdFields(TimeslotEntity entity, PreparedStatement statement) throws SQLException {
        statement.setDate(1, Date.valueOf(entity.getDate()));
        statement.setTime(2, Time.valueOf(entity.getFromTime()));
        statement.setInt(3, entity.getOrder().getId());
        statement.setInt(4, entity.getDuration().getId());
    }
}
