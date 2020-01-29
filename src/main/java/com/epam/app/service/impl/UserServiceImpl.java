package com.epam.app.service.impl;

import com.epam.app.entity.User;
import com.epam.app.dao.domain.Page;
import com.epam.app.dao.domain.PageProperties;
import com.epam.app.dao.UserDao;
import com.epam.app.service.PasswordEncryptor;
import com.epam.app.service.UserService;
import com.epam.app.service.exception.UserAlreadyExistsException;
import com.epam.app.service.validator.Validator;
import com.epam.app.utility.Config;

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
        return User.builder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setPassword(passwordEncryptor.encrypt(user.getPassword(), Config.PASSWORD_SALT))
                .build();
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
