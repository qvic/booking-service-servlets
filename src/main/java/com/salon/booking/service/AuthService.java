package com.salon.booking.service;

import com.salon.booking.domain.User;
import com.salon.booking.domain.UserLoginForm;

import java.util.Optional;

public interface AuthService {

    Optional<User> login(UserLoginForm loginForm);

    User register(User user);
}
