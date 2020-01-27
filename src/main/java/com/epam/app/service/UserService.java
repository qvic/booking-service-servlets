package com.epam.app.service;

import com.epam.app.domain.User;
import com.epam.app.dao.Page;
import com.epam.app.dao.PageProperties;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password);

    User register(User user);

    Page<User> findAll(PageProperties properties);
}
