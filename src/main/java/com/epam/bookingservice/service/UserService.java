package com.epam.bookingservice.service;

import com.epam.bookingservice.domain.Page;
import com.epam.bookingservice.domain.PageProperties;
import com.epam.bookingservice.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password);

    User register(User user);

    Page<User> findAll(PageProperties properties);
}
