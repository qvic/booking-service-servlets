package com.epam.app.service;

import com.epam.app.entity.User;
import com.epam.app.dao.domain.Page;
import com.epam.app.dao.domain.PageProperties;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password);

    User register(User user);

    Page<User> findAll(PageProperties properties);
}
