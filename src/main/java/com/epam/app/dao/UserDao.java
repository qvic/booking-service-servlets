package com.epam.app.dao;

import com.epam.app.domain.User;

import java.util.Optional;

public interface UserDao extends CrudPagingDao<User> {

    Optional<User> findByEmail(String email);
}
