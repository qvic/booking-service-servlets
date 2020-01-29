package com.epam.app.dao;

import com.epam.app.entity.User;

import java.util.Optional;

public interface UserDao extends PageableCrudDao<User> {

    Optional<User> findByEmail(String email);
}
