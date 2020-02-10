package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao extends PageableCrudDao<UserEntity> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllWorkers();
}
