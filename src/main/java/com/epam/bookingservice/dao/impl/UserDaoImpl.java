package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.entity.Role;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.utility.DatabaseConnector;
import com.epam.bookingservice.utility.SimpleDatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractPageableCrudDaoImpl<User> implements UserDao {

    private static final String FIND_BY_ID_QUERY = "SELECT u.id, u.name, u.email, u.password, u.role_id, r.name AS role_name FROM \"user\" u INNER JOIN role r ON u.role_id = r.id WHERE u.id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT u.id, u.name, u.email, u.password, u.role_id, r.name AS role_name FROM \"user\" u INNER JOIN role r ON u.role_id = r.id WHERE u.email = ?";
    private static final String FIND_BY_ROLE_NAME_QUERY = "SELECT u.id, u.name, u.email, u.password, u.role_id, r.name AS role_name FROM \"user\" u INNER JOIN role r ON u.role_id = r.id WHERE r.name = ?";
    private static final String FIND_ALL_QUERY = "SELECT u.id, u.name, u.email, u.password, u.role_id, r.name AS role_name FROM \"user\" u INNER JOIN role r ON u.role_id = r.id";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT u.id, u.name, u.email, u.password, u.role_id, r.name AS role_name FROM \"user\" u INNER JOIN role r ON u.role_id = r.id OFFSET ? LIMIT ?";

    private static final String SAVE_QUERY = "INSERT INTO \"user\" (name, email, password, role_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE \"user\" SET name = ?, email = ?, password = ?, role_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM \"user\" WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM \"user\"";

    public UserDaoImpl(DatabaseConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findByParam(email, FIND_BY_EMAIL_QUERY, STRING_SETTER);
    }

    @Override
    public List<User> findAllByRoleName(String roleName) {
        return findAllByParam(roleName, FIND_BY_ROLE_NAME_QUERY, STRING_SETTER);
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .setId(resultSet.getInt("id"))
                .setEmail(resultSet.getString("email"))
                .setName(resultSet.getString("name"))
                .setPassword(resultSet.getString("password"))
                .setRole(new Role(resultSet.getInt("role_id"), resultSet.getString("role_name")))
                .build();
    }

    @Override
    protected User applyGeneratedKeysToEntity(User entity, ResultSet generatedKeys) throws SQLException {
        entity.setId(generatedKeys.getInt("id"));
        return entity;
    }

    @Override
    protected void populateInsertStatement(User entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(User entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(5, entity.getId());
    }

    private void populateNonIdFields(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getPassword());
        statement.setInt(4, entity.getRole().getId());
    }
}
