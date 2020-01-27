package com.epam.app.dao.impl;

import com.epam.app.dao.AppointmentDao;
import com.epam.app.domain.Appointment;
import com.epam.app.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDaoImpl extends AbstractCrudPagingDaoImpl<Appointment> implements AppointmentDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM appointment WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO appointment (date, worker_id, client_id, service_type_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM appointment";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM appointment OFFSET ? LIMIT ?";
    private static final String UPDATE_QUERY = "UPDATE appointment SET date = ?, worker_id = ?, client_id = ?, service_type_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM appointment WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM appointment";

    public AppointmentDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, INSERT_QUERY, UPDATE_QUERY,
                FIND_ALL_QUERY, COUNT_QUERY, DELETE_QUERY, FIND_ALL_PAGED_QUERY);
    }

    @Override
    protected Appointment mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected Appointment applyGeneratedIdToEntity(Appointment entity, ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected void populateInsertStatement(Appointment entity, PreparedStatement statement) throws SQLException {

    }

    @Override
    protected void populateUpdateStatement(Appointment entity, PreparedStatement statement) throws SQLException {

    }
}
