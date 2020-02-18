package com.bookingservice.service.impl;

import com.bookingservice.dao.UserDao;
import com.bookingservice.domain.User;
import com.bookingservice.domain.page.Page;
import com.bookingservice.domain.page.PageProperties;
import com.bookingservice.entity.UserEntity;
import com.bookingservice.mapper.Mapper;
import com.bookingservice.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final Mapper<UserEntity, User> userMapper;

    public UserServiceImpl(UserDao userDao, Mapper<UserEntity, User> userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
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
    public Page<User> findAllWorkers(PageProperties properties) {
        return userDao.findAllWorkers(properties)
                .map(userMapper::mapEntityToDomain);
    }
}
