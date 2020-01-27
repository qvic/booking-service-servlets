package com.epam.app.dao;

import com.epam.app.domain.Role;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role> {

    Optional<Role> findByName(String name);
}
