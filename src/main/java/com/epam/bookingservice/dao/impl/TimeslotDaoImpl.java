package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.entity.Review;
import com.epam.bookingservice.entity.ReviewStatus;
import com.epam.bookingservice.entity.Timeslot;
import com.epam.bookingservice.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class TimeslotDaoImpl extends AbstractPageableCrudDaoImpl<Timeslot> implements TimeslotDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM timeslot WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM timeslot";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM timeslot OFFSET ? LIMIT ?";

    private static final String SAVE_QUERY = "INSERT INTO timeslot (weekday, from_time, to_time) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE timeslot SET weekday = ?, from_time = ?, to_time = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM review WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM timeslot";

    public TimeslotDaoImpl(DatabaseConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    protected Timeslot mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Timeslot(
                resultSet.getInt("id"),
                resultSet.getInt("weekday"),
                resultSet.getTime("from_time").toLocalTime(),
                resultSet.getTime("to_time").toLocalTime());
    }

    @Override
    protected Timeslot applyGeneratedKeysToEntity(Timeslot entity, ResultSet generatedKeys) throws SQLException {
        return new Timeslot(
                generatedKeys.getInt("id"),
                entity.getWeekday(),
                entity.getFrom(),
                entity.getTo());
    }

    @Override
    protected void populateInsertStatement(Timeslot entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(Timeslot entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(4, entity.getId());
    }

    private void populateNonIdFields(Timeslot entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getWeekday());
        statement.setTime(2, Time.valueOf(entity.getFrom()));
        statement.setTime(3, Time.valueOf(entity.getFrom()));
    }
}
