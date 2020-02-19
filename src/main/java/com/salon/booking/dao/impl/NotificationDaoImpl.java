package com.salon.booking.dao.impl;

import com.salon.booking.dao.NotificationDao;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.NotificationTypeEntity;
import com.salon.booking.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationDaoImpl extends AbstractCrudDaoImpl<NotificationEntity> implements NotificationDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM notification WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM notification";
    private static final String COUNT_QUERY = "SELECT count(*) FROM notification";

    private static final String INSERT_QUERY = "INSERT INTO notification (user_id, type, text) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE notification SET user_id = ?, type = ?, text = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM notification WHERE id = ?";

    public NotificationDaoImpl(DataSourceConnector connector) {
        super(connector, new CrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, INSERT_QUERY,
                UPDATE_QUERY, DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    protected NotificationEntity applyGeneratedKeysToEntity(NotificationEntity entity, ResultSet generatedKeys) throws SQLException {
        return NotificationEntity.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
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

        return NotificationEntity.builder()
                .setId(resultSet.getInt("id"))
                .setText(resultSet.getString("text"))
                .setUser(UserEntity.builder()
                        .setId(resultSet.getInt("user_id"))
                        .build())
                .setType(type)
                .build();
    }
}
