package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.entity.Timeslot;
import com.epam.bookingservice.utility.DatabaseConnector;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class TimeslotDaoImpl extends AbstractCrudDaoImpl<Timeslot> implements TimeslotDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM timeslot WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM timeslot";

    private static final String SAVE_QUERY = "INSERT INTO timeslot (weekday, from_time, to_time) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE timeslot SET weekday = ?, from_time = ?, to_time = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM review WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM timeslot";

    public TimeslotDaoImpl(DatabaseConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected Timeslot mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Timeslot.builder()
                .setId(resultSet.getInt("id"))
                .setDate(resultSet.getDate("date").toLocalDate())
                .setFromTime(resultSet.getTime("from_time").toLocalTime())
                .setToTime(resultSet.getTime("to_time").toLocalTime())
                .build();
    }

    @Override
    protected Timeslot applyGeneratedKeysToEntity(Timeslot entity, ResultSet generatedKeys) throws SQLException {
        return Timeslot.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
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
        statement.setDate(1, Date.valueOf(entity.getDate()));
        statement.setTime(2, Time.valueOf(entity.getFromTime()));
        statement.setTime(3, Time.valueOf(entity.getFromTime()));
    }
}
