package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.ReviewDao;
import com.epam.bookingservice.entity.Review;
import com.epam.bookingservice.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReviewDaoImpl extends AbstractPageableCrudDaoImpl<Review> implements ReviewDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM review WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM review";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM review OFFSET ? LIMIT ?";
    private static final String FIND_ALL_REVIEWS_BY_APPOINTMENT_ID_QUERY = "SELECT r.id, r.text " +
            "FROM review r INNER JOIN appointment a ON r.appointment_id = a.id WHERE a.id = ?";

    private static final String SAVE_QUERY = "INSERT INTO review (appointment_id, text) VALUES (?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE review SET appointment_id = ?, text = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM review WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM review";

    public ReviewDaoImpl(DatabaseConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public List<Review> findReviewsByAppointment(Integer appointmentId) {
        return findAllByParam(appointmentId, FIND_ALL_REVIEWS_BY_APPOINTMENT_ID_QUERY, INT_SETTER);
    }

    @Override
    protected Review mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Review(resultSet.getInt("id"), resultSet.getString("text"));
    }

    @Override
    protected Review applyGeneratedKeysToEntity(Review entity, ResultSet generatedKeys) throws SQLException {
        return new Review(generatedKeys.getInt("id"), entity.getText());
    }

    @Override
    protected void populateInsertStatement(Review entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getText());
    }

    @Override
    protected void populateUpdateStatement(Review entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getText());
        statement.setInt(2, entity.getId());
    }
}
