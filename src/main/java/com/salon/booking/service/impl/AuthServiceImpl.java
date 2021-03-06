package com.salon.booking.service.impl;

import com.salon.booking.dao.UserDao;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.UserLoginForm;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.AuthService;
import com.salon.booking.service.encoder.PasswordEncoder;
import com.salon.booking.service.exception.UserAlreadyExistsException;
import com.salon.booking.service.validator.Validator;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final Validator<User> userValidator;
    private final Validator<UserLoginForm> userLoginFormValidator;

    private final PasswordEncoder passwordEncoder;

    private final Mapper<UserEntity, User> userMapper;

    public AuthServiceImpl(UserDao userDao,
                           Validator<User> userValidator, Validator<UserLoginForm> userLoginFormValidator,
                           PasswordEncoder passwordEncoder, Mapper<UserEntity, User> userMapper) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.userLoginFormValidator = userLoginFormValidator;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> login(UserLoginForm loginForm) {
        userLoginFormValidator.validate(loginForm);

        Optional<UserEntity> user = userDao.findByEmail(loginForm.getEmail());

        if (user.isPresent()) {
            return user.filter(u -> passwordEncoder.verify(loginForm.getPassword(), u.getPassword()))
                    .map(userMapper::mapEntityToDomain);
        }

        return Optional.empty();
    }

    @Override
    public User register(User user) {
        userValidator.validate(user);

        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User encodedUser = prepareUser(user);
        UserEntity savedEntity = userDao.save(userMapper.mapDomainToEntity(encodedUser));

        return userMapper.mapEntityToDomain(savedEntity);
    }

    private User prepareUser(User user) {
        return User.builder(user)
                .setRole(Role.CLIENT)
                .setPassword(passwordEncoder.encode(user.getPassword()))
                .build();
    }
}
