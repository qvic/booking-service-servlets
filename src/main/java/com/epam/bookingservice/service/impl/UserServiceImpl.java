package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.domain.Role;
import com.epam.bookingservice.domain.page.Page;
import com.epam.bookingservice.domain.page.PageProperties;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.service.encoder.PasswordEncoder;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.service.exception.UserAlreadyExistsException;
import com.epam.bookingservice.service.validator.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final Validator<User> userValidator;
    private final Validator<String> emailValidator;

    private final PasswordEncoder passwordEncoder;

    private final Mapper<UserEntity, User> userMapper;

    public UserServiceImpl(UserDao userDao,
                           Validator<User> userValidator, Validator<String> emailValidator,
                           PasswordEncoder passwordEncoder, Mapper<UserEntity, User> userMapper) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.emailValidator = emailValidator;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> login(String email, String password) {
        emailValidator.validate(email);
        Optional<UserEntity> user = userDao.findByEmail(email);

        if (user.isPresent()) {
            return user.filter(u -> passwordEncoder.verify(password, u.getPassword()))
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

    @Override
    public Page<User> findAll(PageProperties properties) {
        Page<UserEntity> page = userDao.findAll(properties);

        if (page.getProperties().getPageNumber() >= page.getTotalPages()) {
            page = userDao.findAll(new PageProperties(page.getTotalPages() - 1, properties.getItemsPerPage()));
        }

        return page.map(userMapper::mapEntityToDomain);
    }

    @Override
    public List<User> findAllWorkers() {
        return userDao.findAllWorkers()
                .stream()
                .map(userMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }
}
