package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.ReviewDao;
import com.epam.bookingservice.entity.Review;
import com.epam.bookingservice.entity.ReviewStatus;
import com.epam.bookingservice.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDaoImpl extends AbstractPageableCrudDaoImpl<Review> implements ReviewDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM review WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM review";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM review OFFSET ? LIMIT ?";

    private static final String SAVE_QUERY = "INSERT INTO review (status_id, text) VALUES (?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE review SET status_id = ?, text = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM review WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM review";

    public ReviewDaoImpl(DatabaseConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    protected Review mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Review(
                resultSet.getInt("id"),
                resultSet.getString("text"),
                ReviewStatus.getById(resultSet.getInt("status_id")));
    }

    @Override
    protected Review applyGeneratedKeysToEntity(Review entity, ResultSet generatedKeys) throws SQLException {
        return new Review(
                generatedKeys.getInt("id"),
                entity.getText(),
                entity.getStatus());
    }

    @Override
    protected void populateInsertStatement(Review entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getStatus().getId());
        statement.setString(2, entity.getText());
    }

    @Override
    protected void populateUpdateStatement(Review entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getStatus().getId());
        statement.setString(2, entity.getText());
        statement.setInt(3, entity.getId());
    }
}
