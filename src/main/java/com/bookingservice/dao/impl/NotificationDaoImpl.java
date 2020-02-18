package com.bookingservice.dao.impl;

import com.bookingservice.dao.NotificationDao;
import com.bookingservice.dao.impl.connector.DataSourceConnector;
import com.bookingservice.domain.User;
import com.bookingservice.entity.NotificationEntity;
import com.bookingservice.entity.NotificationTypeEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationDaoImpl extends AbstractCrudDaoImpl<NotificationEntity> implements NotificationDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM notification WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM notification";
    private static final String COUNT_QUERY = "SELECT count(*) FROM notification";

    private static final String INSERT_QUERY = "INSERT INTO notification (user_id, type) VALUES (?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE notification SET user_id = ?, type = ? WHERE id = ?";
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
                entity.getUser(),
                entity.getType());
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
        statement.setInt(1, entity.getUser().getId());
        statement.setString(2, entity.getType().name());
    }

    @Override
    protected NotificationEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        NotificationTypeEntity type = NotificationTypeEntity.findByName(resultSet.getString("type")).orElse(null);

        return new NotificationEntity(
                resultSet.getInt("id"),
                User.builder()
                        .setId(resultSet.getInt("user_id"))
                        .build(),
                type);
    }
}
