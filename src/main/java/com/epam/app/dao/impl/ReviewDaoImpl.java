package com.epam.app.dao.impl;

import com.epam.app.dao.ReviewDao;
import com.epam.app.domain.Review;
import com.epam.app.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDaoImpl extends AbstractCrudPagingDaoImpl<Review> implements ReviewDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM review WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO review (appointment_id, text) VALUES (?, ?) RETURNING id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM review";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM review OFFSET ? LIMIT ?";
    private static final String UPDATE_QUERY = "UPDATE review SET appointment_id = ?, text = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM review WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM review";

    public ReviewDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, INSERT_QUERY, UPDATE_QUERY,
                FIND_ALL_QUERY, COUNT_QUERY, DELETE_QUERY, FIND_ALL_PAGED_QUERY);
    }

    @Override
    protected Review mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected Review applyGeneratedIdToEntity(Review entity, ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected void populateInsertStatement(Review entity, PreparedStatement statement) throws SQLException {

    }

    @Override
    protected void populateUpdateStatement(Review entity, PreparedStatement statement) throws SQLException {

    }
}
