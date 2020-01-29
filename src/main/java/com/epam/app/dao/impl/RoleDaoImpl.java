package com.epam.app.dao.impl;

import com.epam.app.dao.RoleDao;
import com.epam.app.dao.domain.CrudQuerySet;
import com.epam.app.entity.Role;
import com.epam.app.utility.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RoleDaoImpl extends AbstractCrudDaoImpl<Role> implements RoleDao {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM role WHERE id=?";
    private static final String SAVE_QUERY = "INSERT INTO role (name) VALUES (?) RETURNING id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM role";
    private static final String UPDATE_QUERY = "UPDATE role SET name = ? WHERE id = ?";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM role WHERE name=?";
    private static final String DELETE_QUERY = "DELETE FROM role WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT count(*) FROM role";

    public RoleDaoImpl(DatabaseConnector connector) {
        super(connector, new CrudQuerySet(FIND_BY_ID_QUERY, FIND_ALL_QUERY, SAVE_QUERY, UPDATE_QUERY, DELETE_QUERY, COUNT_QUERY));
    }

    @Override
    public Optional<Role> findByName(String name) {
        return findByParam(name, FIND_BY_NAME_QUERY, STRING_SETTER);
    }

    @Override
    protected Role mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Role(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    protected Role applyGeneratedKeysToEntity(Role entity, ResultSet generatedKeys) throws SQLException {
        entity.setId(generatedKeys.getInt("id"));
        return entity;
    }

    @Override
    protected void populateInsertStatement(Role entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
    }

    @Override
    protected void populateUpdateStatement(Role entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getId());
    }
}
