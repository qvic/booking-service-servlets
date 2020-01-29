package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.AppointmentDao;
import com.epam.bookingservice.dao.ServiceTypeDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.entity.Appointment;
import com.epam.bookingservice.entity.ServiceType;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.utility.DatabaseConnector;
import com.epam.bookingservice.utility.SimpleDatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class AppointmentDaoImpl extends AbstractPageableCrudDaoImpl<Appointment> implements AppointmentDao {

    private static final String FIND_BY_ID_QUERY = "SELECT a.id, a.date, a.worker_id, a.client_id, a.service_id FROM appointment a WHERE a.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT a.id, a.date, a.worker_id, a.client_id, a.service_id FROM appointment a";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT a.id, a.date, a.worker_id, a.client_id, a.service_id FROM appointment a OFFSET ? LIMIT ?";
    private static final String FIND_APPOINTMENT_BY_REVIEW_ID_QUERY = "SELECT a.id, a.date, a.worker_id, a.client_id, a.service_id FROM review r INNER JOIN appointment a ON r.appointment_id = a.id WHERE r.id = ?";

    private static final String SAVE_QUERY = "INSERT INTO appointment (date, worker_id, client_id, service_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE appointment SET date = ?, worker_id = ?, client_id = ?, service_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM appointment WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM appointment";

    private final UserDao userDao;
    private final ServiceTypeDao serviceTypeDao;

    public AppointmentDaoImpl(DatabaseConnector connector, UserDao userDao, ServiceTypeDao serviceTypeDao) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
        this.userDao = userDao;
        this.serviceTypeDao = serviceTypeDao;
    }

    @Override
    public Optional<Appointment> findAppointmentByReview(Integer reviewId) {
        return findByParam(reviewId, FIND_APPOINTMENT_BY_REVIEW_ID_QUERY, INT_SETTER);
    }

    @Override
    protected Appointment mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        // todo another way
        User client = userDao.findById(resultSet.getInt("client_id"))
                .orElseThrow(() -> new DatabaseRuntimeException("No client associated with client_id"));
        User worker = userDao.findById(resultSet.getInt("worker_id"))
                .orElseThrow(() -> new DatabaseRuntimeException("No worker associated with worker_id"));
        ServiceType service = serviceTypeDao.findById(resultSet.getInt("service_id"))
                .orElseThrow(() -> new DatabaseRuntimeException("No service associated with service_id"));

        return Appointment.builder()
                .setId(resultSet.getInt("id"))
                .setClient(client)
                .setWorker(worker)
                .setDate(resultSet.getTimestamp("date").toLocalDateTime())
                .setServiceType(service)
                .build();
    }

    @Override
    protected Appointment applyGeneratedKeysToEntity(Appointment entity, ResultSet generatedKeys) throws SQLException {
        entity.setId(generatedKeys.getInt("id"));
        return entity;
    }

    @Override
    protected void populateInsertStatement(Appointment entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(Appointment entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(5, entity.getId());
    }

    private void populateNonIdFields(Appointment entity, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(entity.getDate()));
        statement.setInt(2, entity.getWorker().getId());
        statement.setInt(3, entity.getClient().getId());
        statement.setInt(4, entity.getServiceType().getId());
    }
}
