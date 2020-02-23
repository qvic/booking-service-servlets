package com.salon.booking.dao.impl;

import com.salon.booking.dao.FeedbackDao;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import com.salon.booking.dao.impl.connector.DataSourceConnection;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.OrderEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackDaoImpl extends AbstractPageableCrudDaoImpl<FeedbackEntity> implements FeedbackDao {

    private static final String FIND_BY_ID_QUERY = "SELECT f.* FROM feedback f WHERE f.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT f.* FROM feedback f";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT f.* FROM feedback f OFFSET ? LIMIT ?";
    private static final String FIND_ALL_APPROVED_WITH_WORKER_QUERY = "SELECT f.* FROM feedback f INNER JOIN \"order\" o on f.order_id = o.id WHERE f.status = 'APPROVED' AND o.worker_id = ? OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_CLIENT_QUERY = "SELECT f.* FROM feedback f INNER JOIN \"order\" o on f.order_id = o.id WHERE o.client_id = ? OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_STATUS_QUERY = "SELECT f.* FROM feedback f WHERE f.status = ? OFFSET ? LIMIT ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM feedback f";
    private static final String COUNT_APPROVED_WITH_WORKER_QUERY = "SELECT count(*) FROM feedback f INNER JOIN \"order\" o ON f.order_id = o.id WHERE f.status = 'APPROVED' AND o.worker_id = ?";
    private static final String COUNT_BY_CLIENT_QUERY = "SELECT count(*) FROM feedback f INNER JOIN \"order\" o ON f.order_id = o.id WHERE f.status = 'APPROVED' AND o.client_id = ?";
    private static final String COUNT_BY_STATUS_QUERY = "SELECT count(*) FROM feedback f WHERE f.status = ?";

    private static final String SAVE_QUERY = "INSERT INTO feedback (text, status, order_id) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE feedback SET text = ?, status = ?, order_id = ? WHERE id = ?";
    private static final String UPDATE_FEEDBACK_STATUS_QUERY = "UPDATE feedback SET status = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM feedback WHERE id = ?";

    public FeedbackDaoImpl(DataSourceConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public Page<FeedbackEntity> findAllApprovedWithWorkerId(Integer workerId, PageProperties properties) {
        return findPageByParam(workerId, FIND_ALL_APPROVED_WITH_WORKER_QUERY, COUNT_APPROVED_WITH_WORKER_QUERY, INT_SETTER, properties);
    }

    @Override
    public Page<FeedbackEntity> findAllByClientId(Integer clientId, PageProperties properties) {
        return findPageByParam(clientId, FIND_ALL_BY_CLIENT_QUERY, COUNT_BY_CLIENT_QUERY, INT_SETTER, properties);
    }

    @Override
    public Page<FeedbackEntity> findAllByStatus(FeedbackStatusEntity status, PageProperties properties) {
        return findPageByParam(status.name(), FIND_ALL_BY_STATUS_QUERY, COUNT_BY_STATUS_QUERY, STRING_SETTER, properties);
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
                .setOrder(OrderEntity.builder()
                        .setId(resultSet.getInt("order_id"))
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
        statement.setString(2, entity.getStatus().name());
        statement.setInt(3, entity.getOrder().getId());
    }
}
