package com.epam.app.dao.impl;

import com.epam.app.dao.UserDao;
import com.epam.app.dao.exception.DatabaseRuntimeException;
import com.epam.app.domain.User;
import com.epam.app.utility.DatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractCrudPagingDaoImpl<User> implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM \"user\" WHERE id=?";
    private static final String INSERT_QUERY = "INSERT INTO \"user\" (name, email, password, role_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM \"user\"";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT * FROM \"user\" OFFSET ? LIMIT ?";
    private static final String UPDATE_QUERY = "UPDATE \"user\" SET name = ?, email = ?, password = ?, role_id = ? WHERE id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM \"user\" WHERE email=?";
    private static final String DELETE_QUERY = "DELETE FROM \"user\" WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM \"user\"";

    public UserDaoImpl(DatabaseConnector connector) {
        super(connector, FIND_BY_ID_QUERY, INSERT_QUERY, UPDATE_QUERY,
                FIND_ALL_QUERY, COUNT_QUERY, DELETE_QUERY, FIND_ALL_PAGED_QUERY);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (PreparedStatement statement = connector.getConnection().prepareStatement(FIND_BY_EMAIL_QUERY)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseRuntimeException("Error initializing PreparedStatement", e);
        }

        return Optional.empty();
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .setId(resultSet.getInt("id"))
                .setEmail(resultSet.getString("email"))
                .setName(resultSet.getString("name"))
                .setPassword(resultSet.getString("password"))
                .build();
    }

    @Override
    protected User applyGeneratedIdToEntity(User entity, ResultSet resultSet) throws SQLException {
        return User.builder(entity)
                .setId(resultSet.getInt(1))
                .build();
    }

    @Override
    protected void populateInsertStatement(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getPassword());
        statement.setInt(4, entity.getRole().getId());
    }

    @Override
    protected void populateUpdateStatement(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getPassword());
        statement.setInt(4, entity.getRole().getId());
        statement.setInt(5, entity.getId());
    }
}
