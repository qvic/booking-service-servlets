package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.dao.Page;
import com.epam.bookingservice.dao.PageProperties;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.service.PasswordEncryptor;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.service.exception.UserAlreadyExistsException;
import com.epam.bookingservice.service.validator.Validator;
import com.epam.bookingservice.utility.Config;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userRepository;
    private final Validator<User> userValidator;
    private final PasswordEncryptor passwordEncryptor;

    public UserServiceImpl(UserDao userRepository, Validator<User> userValidator, PasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public Optional<User> login(String email, String password) {
        String hashedPassword = passwordEncryptor.encrypt(password, Config.PASSWORD_SALT);

        return userRepository.findByEmail(email)
                .filter(s -> s.getPassword().equals(hashedPassword));
    }

    @Override
    public User register(User user) {
        userValidator.validate(user);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(encryptPassword(user));
    }

    private User encryptPassword(User user) {
        user.setPassword(passwordEncryptor.encrypt(user.getPassword(), Config.PASSWORD_SALT));
        return user;
    }

    @Override
    public Page<User> findAll(PageProperties properties) {
        Page<User> page = userRepository.findAll(properties);
        if (page.getProperties().getPageNumber() >= page.getTotalPages()) {
            page = userRepository.findAll(new PageProperties(page.getTotalPages() - 1, properties.getItemsPerPage()));
        }
        return page;
    }
}
