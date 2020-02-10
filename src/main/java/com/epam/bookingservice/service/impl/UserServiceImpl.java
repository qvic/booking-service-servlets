package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.domain.page.Page;
import com.epam.bookingservice.domain.page.PageProperties;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.service.encryptor.PasswordEncryptor;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.service.exception.UserAlreadyExistsException;
import com.epam.bookingservice.service.validator.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static final String PASSWORD_SALT = "salt";

    private final UserDao userDao;
    private final Validator<User> userValidator;
    private final Validator<String> emailValidator;

    private final PasswordEncryptor passwordEncryptor;

    private final Mapper<UserEntity, User> userMapper;

    public UserServiceImpl(UserDao userDao,
                           Validator<User> userValidator, Validator<String> emailValidator,
                           PasswordEncryptor passwordEncryptor, Mapper<UserEntity, User> userMapper) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.emailValidator = emailValidator;
        this.passwordEncryptor = passwordEncryptor;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> login(String email, String password) {
        emailValidator.validate(email);
        Optional<UserEntity> userEntityByEmail = userDao.findByEmail(email);

        if (userEntityByEmail.isPresent()) {
            String hashedPassword = passwordEncryptor.encrypt(password, PASSWORD_SALT);

            return userEntityByEmail
                    .filter(s -> s.getPassword().equals(hashedPassword))
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

        User encryptedUser = encryptPassword(user);
        UserEntity savedEntity = userDao.save(userMapper.mapDomainToEntity(encryptedUser));

        return userMapper.mapEntityToDomain(savedEntity);
    }

    private User encryptPassword(User user) {
        return User.builder(user)
                .setPassword(passwordEncryptor.encrypt(user.getPassword(), PASSWORD_SALT))
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
