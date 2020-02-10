package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.dao.impl.connector.DataSourceConnector;
import com.epam.bookingservice.entity.RoleEntity;
import com.epam.bookingservice.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractPageableCrudDaoImpl<UserEntity> implements UserDao {

    private static final String FIND_BY_ID_QUERY = "SELECT u.*, r.name as role_name FROM \"user\" u INNER JOIN role r on u.role_id = r.id WHERE u.id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT u.*, r.name as role_name FROM \"user\" u INNER JOIN role r on u.role_id = r.id WHERE u.email = ?";
    private static final String FIND_ALL_QUERY = "SELECT u.*, r.name as role_name FROM \"user\" u INNER JOIN role r on u.role_id = r.id";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT u.*, r.name as role_name FROM \"user\" u INNER JOIN role r on u.role_id = r.id OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_ROLE_QUERY = "SELECT u.*, r.name as role_name FROM \"user\" u INNER JOIN role r on u.role_id = r.id WHERE u.role_id = ?";

    private static final String SAVE_QUERY = "INSERT INTO \"user\" (name, email, password, role_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE \"user\" SET name = ?, email = ?, password = ?, role_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM \"user\" WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM \"user\"";

    public UserDaoImpl(DataSourceConnector connector) {
        super(connector, new PageableCrudQuerySet(
                FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY,
                DELETE_QUERY, COUNT_QUERY, FIND_ALL_PAGED_QUERY));
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return findByParam(email, FIND_BY_EMAIL_QUERY, STRING_SETTER);
    }

    @Override
    public List<UserEntity> findAllWorkers() {
        return findAllByParam(RoleEntity.WORKER.getId(), FIND_ALL_BY_ROLE_QUERY, INT_SETTER);
    }

    @Override
    protected UserEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int roleId = resultSet.getInt("role_id");
        String roleName = resultSet.getString("role_name");

        RoleEntity role = RoleEntity.findByIdAndName(roleId, roleName)
                .orElseThrow(() -> new DatabaseRuntimeException(
                        String.format("Mapping exception. Can't find role by id=[%d], name=[%s]", roleId, roleName)));

        return UserEntity.builder()
                .setId(resultSet.getInt("id"))
                .setEmail(resultSet.getString("email"))
                .setName(resultSet.getString("name"))
                .setPassword(resultSet.getString("password"))
                .setRole(role)
                .build();
    }

    @Override
    protected UserEntity applyGeneratedKeysToEntity(UserEntity entity, ResultSet generatedKeys) throws SQLException {
        return UserEntity.builder(entity)
                .setId(generatedKeys.getInt("id"))
                .build();
    }

    @Override
    protected void populateInsertStatement(UserEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
    }

    @Override
    protected void populateUpdateStatement(UserEntity entity, PreparedStatement statement) throws SQLException {
        populateNonIdFields(entity, statement);
        statement.setInt(5, entity.getId());
    }

    private void populateNonIdFields(UserEntity entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getPassword());
        statement.setInt(4, entity.getRole().getId());
    }
}
