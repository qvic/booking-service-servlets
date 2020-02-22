package com.salon.booking.dao.impl;

import com.salon.booking.dao.TimeslotDao;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import com.salon.booking.dao.impl.connector.DataSourceConnection;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.entity.DurationEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.TimeslotEntity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeslotDaoImpl extends AbstractCrudDaoImpl<TimeslotEntity> implements TimeslotDao {

    private static final String FIND_ALL_QUERY = "SELECT t.*, d.minutes, ot.order_id FROM timeslot t INNER JOIN duration d ON t.duration_id = d.id LEFT JOIN order_timeslot ot ON ot.timeslot_id = t.id ORDER BY t.id";
    private static final String FIND_BY_ID_QUERY = "SELECT t.*, d.minutes, ot.order_id FROM timeslot t INNER JOIN duration d ON t.duration_id = d.id LEFT JOIN order_timeslot ot ON ot.timeslot_id = t.id WHERE t.id = ? ORDER BY t.id";
    private static final String FIND_ALL_BETWEEN_DATES = "SELECT t.*, d.minutes, ot.order_id FROM timeslot t INNER JOIN duration d ON t.duration_id = d.id LEFT JOIN order_timeslot ot ON ot.timeslot_id = t.id WHERE t.date >= ? AND t.date < ? ORDER BY t.date, t.from_time, t.id";
    private static final String FIND_ALL_SAME_DAY = "SELECT t.*, d.minutes, ot.order_id FROM timeslot t INNER JOIN duration d ON t.duration_id = d.id LEFT JOIN order_timeslot ot ON ot.timeslot_id = t.id WHERE t.date = (SELECT date FROM timeslot WHERE id = ?) ORDER BY t.from_time, t.id";
    private static final String COUNT_QUERY = "SELECT count(*) FROM timeslot";

    private static final String SAVE_ORDER_TIMESLOT_QUERY = "INSERT INTO order_timeslot (timeslot_id, order_id) VALUES (?, ?)";
    private static final String SAVE_QUERY = "INSERT INTO timeslot (date, from_time, duration_id) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE timeslot SET date = ?, from_time = ?, duration_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM timeslot WHERE id = ?";

    public TimeslotDaoImpl(DataSourceConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected List<TimeslotEntity> getResultList(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            List<TimeslotEntity> timeslots = new ArrayList<>();

            while (resultSet.next()) {
                int timeslotId = resultSet.getInt("id");
                TimeslotEntity lastAdded = timeslots.isEmpty() ? null : timeslots.get(timeslots.size() - 1);

                if (lastAdded == null || lastAdded.getId() != timeslotId) {
                    TimeslotEntity entity = mapResultSetToEntity(resultSet);
                    timeslots.add(entity);
                } else {
                    lastAdded.getOrders().add(mapOrder(resultSet));
                }

            }
            return timeslots;
        }
    }

    @Override
    protected TimeslotEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return TimeslotEntity.builder()
                .setId(resultSet.getInt("id"))
                .setDate(resultSet.getDate("date").toLocalDate())
                .setFromTime(resultSet.getTime("from_time").toLocalTime())
                .setDuration(mapDuration(resultSet))
                .addOrder(mapOrder(resultSet))
                .build();
    }

    private OrderEntity mapOrder(ResultSet resultSet) throws SQLException {
        return OrderEntity.builder()
                .setId(resultSet.getInt("order_id"))
                .build();
    }

    private DurationEntity mapDuration(ResultSet resultSet) throws SQLException {
        return new DurationEntity(
                resultSet.getInt("duration_id"),
                resultSet.getInt("minutes"));
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
    public List<TimeslotEntity> findAllBetweenDatesSorted(LocalDate from, LocalDate to) {
        return findAllByTwoParams(from, to, FIND_ALL_BETWEEN_DATES, LOCAL_DATE_SETTER);
    }

    @Override
    public List<TimeslotEntity> findAllTimeslotsOfTheSameDaySorted(Integer timeslotId) {
        return findAllByParam(timeslotId, FIND_ALL_SAME_DAY, INT_SETTER);
    }

    @Override
    public void saveOrderTimeslot(Integer timeslotId, Integer orderId) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(SAVE_ORDER_TIMESLOT_QUERY)) {

            statement.setInt(1, timeslotId);
            statement.setInt(2, orderId);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing saveOrderTimeslot", e);
        }
    }

    @Override
    protected void populateUpdateStatement(TimeslotEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(4, entity.getId());
    }

    private void populateNonIdFields(TimeslotEntity entity, PreparedStatement statement) throws SQLException {
        statement.setDate(1, Date.valueOf(entity.getDate()));
        statement.setTime(2, Time.valueOf(entity.getFromTime()));
        statement.setInt(3, entity.getDuration().getId());
    }
}
