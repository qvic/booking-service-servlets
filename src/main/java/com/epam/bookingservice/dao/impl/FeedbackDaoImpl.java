package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.FeedbackDao;
import com.epam.bookingservice.entity.Feedback;
import com.epam.bookingservice.entity.FeedbackStatus;
import com.epam.bookingservice.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FeedbackDaoImpl extends AbstractPageableCrudDaoImpl<Feedback> implements FeedbackDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM feedback WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM feedback";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM feedback OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_WORKER = "SELECT * FROM feedback WHERE worker_id = ?";
    private static final String FIND_ALL_BY_STATUS = "SELECT * FROM feedback WHERE status_id = ?";

    private static final String SAVE_QUERY = "INSERT INTO feedback (status_id, text) VALUES (?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE feedback SET status_id = ?, text = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM feedback WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM feedback";

    public FeedbackDaoImpl(DatabaseConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public List<Feedback> findAllByWorkerId(Integer id) {
        // todo pagination here
        return findAllByParam(id, FIND_ALL_BY_WORKER, INT_SETTER);
    }

    @Override
    public List<Feedback> findAllByStatus(FeedbackStatus status) {
        return findAllByParam(status.getId(), FIND_ALL_BY_STATUS, INT_SETTER);
    }

    @Override
    protected Feedback mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Feedback.builder()
                .setId(resultSet.getInt("id"))
                .setText(resultSet.getString("text"))
                .setStatus(FeedbackStatus.getById(resultSet.getInt("status_id")))
                .build();
    }

    @Override
    protected Feedback applyGeneratedKeysToEntity(Feedback entity, ResultSet generatedKeys) throws SQLException {
        return Feedback.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    @Override
    protected void populateInsertStatement(Feedback entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getStatus().getId());
        statement.setString(2, entity.getText());
    }

    @Override
    protected void populateUpdateStatement(Feedback entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getStatus().getId());
        statement.setString(2, entity.getText());
        statement.setInt(3, entity.getId());
    }
}
