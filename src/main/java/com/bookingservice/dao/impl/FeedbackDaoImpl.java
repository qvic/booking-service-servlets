package com.bookingservice.dao.impl;

import com.bookingservice.dao.FeedbackDao;
import com.bookingservice.dao.exception.DatabaseRuntimeException;
import com.bookingservice.dao.impl.connector.DataSourceConnection;
import com.bookingservice.dao.impl.connector.DataSourceConnector;
import com.bookingservice.domain.page.Page;
import com.bookingservice.domain.page.PageProperties;
import com.bookingservice.entity.FeedbackEntity;
import com.bookingservice.entity.FeedbackStatusEntity;
import com.bookingservice.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackDaoImpl extends AbstractPageableCrudDaoImpl<FeedbackEntity> implements FeedbackDao {

    private static final String FIND_BY_ID_QUERY = "SELECT f.* FROM feedback f WHERE f.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT f.* FROM feedback f";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT f.* FROM feedback f OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_WORKER = "SELECT f.* FROM feedback f WHERE f.worker_id = ? OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_STATUS = "SELECT f.* FROM feedback f WHERE f.status = ? OFFSET ? LIMIT ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM feedback";

    private static final String SAVE_QUERY = "INSERT INTO feedback (text, worker_id, status) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE feedback SET text = ?, worker_id = ?, status = ? WHERE id = ?";
    private static final String UPDATE_FEEDBACK_STATUS_QUERY = "UPDATE feedback SET status = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM feedback WHERE id = ?";

    public FeedbackDaoImpl(DataSourceConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public Page<FeedbackEntity> findAllByWorkerId(Integer workerId, PageProperties properties) {
        return findPageByParam(workerId, FIND_ALL_BY_WORKER, INT_SETTER, properties);
    }

    @Override
    public Page<FeedbackEntity> findAllByStatus(FeedbackStatusEntity status, PageProperties properties) {
        return findPageByParam(status.name(), FIND_ALL_BY_STATUS, STRING_SETTER, properties);
    }

    @Override
    public void updateStatus(Integer feedbackId, FeedbackStatusEntity feedbackStatusEntity) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(UPDATE_FEEDBACK_STATUS_QUERY)) {

            statement.setString(1, feedbackStatusEntity.name());
            statement.setInt(2, feedbackId);

            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing updateStatus", e);
        }
    }

    @Override
    protected FeedbackEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return FeedbackEntity.builder()
                .setId(resultSet.getInt("id"))
                .setText(resultSet.getString("text"))
                .setWorker(UserEntity.builder()
                        .setId(resultSet.getInt("worker_id"))
                        .build())
                .setStatus(mapStatus(resultSet))
                .build();
    }

    private FeedbackStatusEntity mapStatus(ResultSet resultSet) throws SQLException {
        return FeedbackStatusEntity.findByName(resultSet.getString("status"))
                .orElseThrow(() -> new DatabaseRuntimeException("Can't map FeedbackStatusEntity from database"));
    }

    @Override
    protected FeedbackEntity applyGeneratedKeysToEntity(FeedbackEntity entity, ResultSet generatedKeys) throws SQLException {
        return FeedbackEntity.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    @Override
    protected void populateInsertStatement(FeedbackEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(FeedbackEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(4, entity.getId());
    }

    private void populateNonIdFields(FeedbackEntity entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getText());
        statement.setInt(2, entity.getWorker().getId());
        statement.setString(3, entity.getStatus().name());
    }
}
