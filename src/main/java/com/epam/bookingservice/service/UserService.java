package com.epam.bookingservice.service;

import com.epam.bookingservice.domain.page.Page;
import com.epam.bookingservice.domain.page.PageProperties;
import com.epam.bookingservice.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password);

    User register(User user);

    Page<User> findAll(PageProperties properties);

    List<User> findAllWorkers();
}
