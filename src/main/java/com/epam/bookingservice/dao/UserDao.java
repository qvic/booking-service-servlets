package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.UserEntity;

import java.util.Optional;

public interface UserDao extends PageableCrudDao<UserEntity> {

    Optional<UserEntity> findByEmail(String email);
}
