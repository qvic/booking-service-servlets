package com.salon.booking.dao.impl;

import com.salon.booking.dao.UserDao;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import com.salon.booking.dao.impl.connector.DataSourceConnector;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.RoleEntity;
import com.salon.booking.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractPageableCrudDaoImpl<UserEntity> implements UserDao {

    private static final String FIND_BY_ID_QUERY = "SELECT u.* FROM \"user\" u WHERE u.id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT u.* FROM \"user\" u WHERE u.email = ?";
    private static final String FIND_ALL_QUERY = "SELECT u.* FROM \"user\" u";
    private static final String FIND_ALL_PAGED_QUERY = "SELECT u.* FROM \"user\" u OFFSET ? LIMIT ?";
    private static final String FIND_ALL_BY_ROLE_QUERY = "SELECT u.* FROM \"user\" u WHERE u.role = ? OFFSET ? LIMIT ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM \"user\" u";
    private static final String COUNT_BY_ROLE_QUERY = "SELECT count(*) FROM \"user\" u WHERE u.role = ?";

    private static final String SAVE_QUERY = "INSERT INTO \"user\" (name, email, password, role) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE \"user\" SET name = ?, email = ?, password = ?, role = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM \"user\" WHERE id = ?";

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
    public Page<UserEntity> findAllByRole(RoleEntity role, PageProperties properties) {
        return findPageByParam(role.name(), FIND_ALL_BY_ROLE_QUERY, COUNT_BY_ROLE_QUERY, STRING_SETTER, properties);
    }

    @Override
    protected UserEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .setId(resultSet.getInt("id"))
                .setEmail(resultSet.getString("email"))
                .setName(resultSet.getString("name"))
                .setPassword(resultSet.getString("password"))
                .setRole(mapRole(resultSet))
                .build();
    }

    private RoleEntity mapRole(ResultSet resultSet) throws SQLException {
        return RoleEntity.findByName(resultSet.getString("role"))
                .orElseThrow(() -> new DatabaseRuntimeException("Can't map RoleEntity from database"));
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
        statement.setString(4, entity.getRole().name());
    }
}
