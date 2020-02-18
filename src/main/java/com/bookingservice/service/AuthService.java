package com.bookingservice.service;

import com.bookingservice.domain.User;
import com.bookingservice.domain.UserLoginForm;
import com.bookingservice.domain.page.Page;
import com.bookingservice.domain.page.PageProperties;

import java.util.List;
import java.util.Optional;

public interface AuthService {

    Optional<User> login(UserLoginForm loginForm);

    User register(User user);
}
