package com.salon.booking.dao;

import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.UserEntity;

import java.util.Optional;

public interface UserDao extends PageableCrudDao<UserEntity> {

    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAllWorkers(PageProperties pageProperties);
}
