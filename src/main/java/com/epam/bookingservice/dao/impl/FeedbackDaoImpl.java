package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.FeedbackDao;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import com.epam.bookingservice.entity.FeedbackEntity;
import com.epam.bookingservice.entity.FeedbackStatusEntity;
import com.epam.bookingservice.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FeedbackDaoImpl extends AbstractPageableCrudDaoImpl<FeedbackEntity> implements FeedbackDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM feedback WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM feedback";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM feedback OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_WORKER = "SELECT * FROM feedback WHERE worker_id = ?";
    private static final String FIND_ALL_BY_STATUS = "SELECT * FROM feedback WHERE status_id = ?";

    private static final String SAVE_QUERY = "INSERT INTO feedback (status_id, text, worker_id) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE feedback SET status_id = ?, text = ?, worker_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM feedback WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM feedback";

    public FeedbackDaoImpl(DataSourceConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public List<FeedbackEntity> findAllByWorkerId(Integer id) {
        // todo pagination here
        return findAllByParam(id, FIND_ALL_BY_WORKER, INT_SETTER);
    }

    @Override
    public List<FeedbackEntity> findAllByStatus(FeedbackStatusEntity status) {
        return findAllByParam(status.getId(), FIND_ALL_BY_STATUS, INT_SETTER);
    }

    @Override
    protected FeedbackEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return FeedbackEntity.builder()
                .setId(resultSet.getInt("id"))
                .setText(resultSet.getString("text"))
                .setStatus(FeedbackStatusEntity.getById(resultSet.getInt("status_id")))
                .setWorker(UserEntity.builder().setId(resultSet.getInt("worker_id")).build())
                .build();
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
        statement.setInt(1, entity.getStatus().getId());
        statement.setString(2, entity.getText());
        statement.setInt(3, entity.getWorker().getId());
    }
}
