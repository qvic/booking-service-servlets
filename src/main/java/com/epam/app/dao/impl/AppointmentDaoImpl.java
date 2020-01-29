package com.epam.app.dao.impl;

import com.epam.app.dao.AppointmentDao;
import com.epam.app.dao.domain.PageableCrudQuerySet;
import com.epam.app.entity.Appointment;
import com.epam.app.entity.Role;
import com.epam.app.entity.ServiceType;
import com.epam.app.entity.User;
import com.epam.app.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Optional;

public class AppointmentDaoImpl extends AbstractPageableCrudDaoImpl<Appointment> implements AppointmentDao {

    // todo workaround
    private static final String FIND_BY_ID_QUERY = "SELECT a.id, a.date, a.worker_id, a.client_id, a.service_id, w.name as worker_name, w.email as worker_email, w.password as worker_password, w.role_id as worker_role_id, w_role.name as worker_role_name, c.name s client_name, c.email as client_email, c.password as client_password, c.role_id as client_role_id, c_role.name as client_role_name, s.name as service_name, s.duration_seconds as service_duration_seconds, s.available_workplaces as service_available_workplaces FROM appointment a " +
            "INNER JOIN \"user\" c ON a.client_id = c.id " +
            "INNER JOIN role AS c_role ON c.role_id = c_role.id " +
            "INNER JOIN \"user\" w ON a.worker_id = w.id " +
            "INNER JOIN role AS w_role ON w.role_id = w_role.id " +
            "INNER JOIN service s on a.service_id = s.id " +
            "WHERE a.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT a.id, a.date, a.worker_id, a.client_id, a.service_id, w.name as worker_name, w.email as worker_email, w.password as worker_password, w.role_id as worker_role_id, w_role.name as worker_role_name, c.name s client_name, c.email as client_email, c.password as client_password, c.role_id as client_role_id, c_role.name as client_role_name, s.name as service_name, s.duration_seconds as service_duration_seconds, s.available_workplaces as service_available_workplaces FROM appointment a " +
            "INNER JOIN \"user\" c ON a.client_id = c.id " +
            "INNER JOIN role AS c_role ON c.role_id = c_role.id " +
            "INNER JOIN \"user\" w ON a.worker_id = w.id " +
            "INNER JOIN role AS w_role ON w.role_id = w_role.id " +
            "INNER JOIN service s on a.service_id = s.id ";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT a.id, a.date, a.worker_id, a.client_id, a.service_id, w.name as worker_name, w.email as worker_email, w.password as worker_password, w.role_id as worker_role_id, w_role.name as worker_role_name, c.name s client_name, c.email as client_email, c.password as client_password, c.role_id as client_role_id, c_role.name as client_role_name, s.name as service_name, s.duration_seconds as service_duration_seconds, s.available_workplaces as service_available_workplaces FROM appointment a " +
            "INNER JOIN \"user\" c ON a.client_id = c.id " +
            "INNER JOIN role AS c_role ON c.role_id = c_role.id " +
            "INNER JOIN \"user\" w ON a.worker_id = w.id " +
            "INNER JOIN role AS w_role ON w.role_id = w_role.id " +
            "INNER JOIN service s on a.service_id = s.id " +
            "OFFSET ? LIMIT ?";
    private static final String FIND_APPOINTMENT_BY_REVIEW_ID_QUERY = "SELECT a.id, a.date, a.worker_id, a.client_id, a.service_id, " +
            "s.name AS service_name, s.duration_seconds AS service_duration_seconds, s.available_workplaces AS service_available_workplaces " +
            "FROM review r INNER JOIN appointment a ON r.appointment_id = a.id INNER JOIN service s ON a.service_id = s.id WHERE r.id = ?";

    private static final String SAVE_QUERY = "INSERT INTO appointment (date, worker_id, client_id, service_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE appointment SET date = ?, worker_id = ?, client_id = ?, service_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM appointment WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM appointment";

    public AppointmentDaoImpl(DatabaseConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public Optional<Appointment> findAppointmentByReview(Integer reviewId) {
        return findByParam(reviewId, FIND_APPOINTMENT_BY_REVIEW_ID_QUERY, INT_SETTER);
    }

    @Override
    protected Appointment mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        User client = User.builder()
                .setId(resultSet.getInt("client_id"))
                .setName(resultSet.getString("client_name"))
                .setEmail(resultSet.getString("client_email"))
                .setPassword(resultSet.getString("client_password"))
                .setRole(new Role(resultSet.getInt("client_role_id"),
                        resultSet.getString("client_role_name")))
                .build();
        User worker = User.builder()
                .setId(resultSet.getInt("worker_id"))
                .setName(resultSet.getString("worker_name"))
                .setEmail(resultSet.getString("worker_email"))
                .setPassword(resultSet.getString("worker_password"))
                .setRole(new Role(resultSet.getInt("worker_role_id"),
                        resultSet.getString("worker_role_name")))
                .build();
        ServiceType service = ServiceType.builder()
                .setId(resultSet.getInt("service_id"))
                .setName(resultSet.getString("service_name"))
                .setAvailableWorkplaces(resultSet.getInt("service_available_workplaces"))
                .setDuration(Duration.ofSeconds(resultSet.getInt("service_duration_seconds")))
                .build();

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
