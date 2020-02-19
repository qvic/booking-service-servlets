package com.salon.booking.service.impl;

import com.salon.booking.dao.UserDao;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.UserService;

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
