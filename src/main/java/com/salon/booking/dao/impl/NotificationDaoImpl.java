package com.salon.booking.dao.impl;

import com.salon.booking.dao.NotificationDao;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import com.salon.booking.dao.impl.connector.DataSourceConnection;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.OrderEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class NotificationDaoImpl extends AbstractCrudDaoImpl<NotificationEntity> implements NotificationDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM notification WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM notification";
    private static final String COUNT_QUERY = "SELECT count(*) FROM notification";
    private static final String FIND_ALL_UNREAD = "SELECT n.* FROM notification n INNER JOIN \"order\" o ON n.order_id = o.id WHERE o.client_id = ? AND read = false";
    private static final String FIND_ALL_READ = "SELECT n.* FROM notification n INNER JOIN \"order\" o ON n.order_id = o.id WHERE o.client_id = ? AND read = true";
    private static final String FIND_BY_ORDER_ID = "SELECT n.* FROM notification n INNER JOIN \"order\" o ON n.order_id = o.id WHERE o.id = ?";

    private static final String INSERT_QUERY = "INSERT INTO notification (order_id, read) VALUES (?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE notification SET order_id = ?, read = ? WHERE id = ?";
    private static final String UPDATE_ALL_AS_READ_QUERY = "UPDATE notification n SET read = true FROM \"order\" o WHERE n.order_id = o.id AND o.client_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM notification WHERE id = ?";

    public NotificationDaoImpl(DataSourceConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, INSERT_QUERY,
                UPDATE_QUERY, DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected NotificationEntity applyGeneratedKeysToEntity(NotificationEntity entity, ResultSet generatedKeys) throws SQLException {
        return new NotificationEntity(
                generatedKeys.getInt("id"),
                entity.getOrder(),
                entity.getRead());
    }

    @Override
    protected void populateInsertStatement(NotificationEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(NotificationEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(3, entity.getId());
    }

    private void populateNonIdFields(NotificationEntity entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getOrder().getId());
        statement.setBoolean(2, entity.getRead());
    }

    @Override
    protected NotificationEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        OrderEntity order = OrderEntity.builder()
                .setId(resultSet.getInt("order_id"))
                .build();

        return new NotificationEntity(
                resultSet.getInt("id"),
                order,
                resultSet.getBoolean("read"));
    }

    @Override
    public List<NotificationEntity> findAllUnread(Integer userId) {
        return findAllByParam(userId, FIND_ALL_UNREAD, INT_SETTER);
    }

    @Override
    public List<NotificationEntity> findAllRead(Integer userId) {
        return findAllByParam(userId, FIND_ALL_READ, INT_SETTER);
    }

    @Override
    public void updateAllAsRead(Integer userId) {
        try (DataSourceConnection connection = connector.getConnection();
             PreparedStatement statement = connection.getOriginal().prepareStatement(UPDATE_ALL_AS_READ_QUERY)) {
            statement.setInt(1, userId);
            int affectedRows = statement.executeUpdate();
            throwIfNotAffected(affectedRows);
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error performing updateAllAsRead", e);
        }
    }

    @Override
    public Optional<NotificationEntity> findByOrderId(Integer orderId) {
        return findByParam(orderId, FIND_BY_ORDER_ID, INT_SETTER);
    }
}
