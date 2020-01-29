package com.epam.bookingservice.service;

import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.dao.Page;
import com.epam.bookingservice.dao.PageProperties;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password);

    User register(User user);

    Page<User> findAll(PageProperties properties);
}
