package com.bookingservice.dao;

import com.bookingservice.domain.page.Page;
import com.bookingservice.domain.page.PageProperties;
import com.bookingservice.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao extends PageableCrudDao<UserEntity> {

    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAllWorkers(PageProperties pageProperties);
}
