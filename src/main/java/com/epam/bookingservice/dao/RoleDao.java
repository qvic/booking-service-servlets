package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.Role;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role> {

    Optional<Role> findByName(String name);
}
