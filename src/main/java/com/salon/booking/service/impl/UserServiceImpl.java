package com.salon.booking.service.impl;

import com.salon.booking.dao.TransactionManager;
import com.salon.booking.dao.UserDao;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.RoleEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.UserService;
import com.salon.booking.service.exception.NoSuchUserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final Mapper<UserEntity, User> userMapper;
    private TransactionManager transactionManager;

    public UserServiceImpl(UserDao userDao, Mapper<UserEntity, User> userMapper, TransactionManager transactionManager) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.transactionManager = transactionManager;
    }

    @Override
    public Page<User> findAllWorkers(PageProperties properties) {
        return findAllByRole(properties, RoleEntity.WORKER);
    }

    @Override
    public Page<User> findAllClients(PageProperties properties) {
        return findAllByRole(properties, RoleEntity.CLIENT);
    }

    private Page<User> findAllByRole(PageProperties properties, RoleEntity worker) {
        Page<UserEntity> page = userDao.findAllByRole(worker, properties);

        if (page.getProperties().getPageNumber() >= page.getTotalPages()) {
            page = userDao.findAllByRole(worker, new PageProperties(page.getTotalPages() - 1, properties.getItemsPerPage()));
        }

        return page.map(userMapper::mapEntityToDomain);
    }

    @Override
    public void promoteToWorker(Integer clientId) {
        try {
            transactionManager.beginTransaction();

            UserEntity updatedUser = userDao.findById(clientId)
                    .filter(user -> user.getRole().equals(RoleEntity.CLIENT))
                    .map(user -> UserEntity.builder(user)
                            .setRole(RoleEntity.WORKER)
                            .build())
                    .orElseThrow(NoSuchUserException::new);

            userDao.update(updatedUser);

            transactionManager.commitTransaction();
        } catch (DatabaseRuntimeException e) {
            LOGGER.error(e);
            transactionManager.rollbackTransaction();
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userDao.findById(id)
                .map(userMapper::mapEntityToDomain);
    }
}
