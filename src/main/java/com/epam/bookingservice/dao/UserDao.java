package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.Role;
import com.epam.bookingservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends PageableCrudDao<User> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRoleName(String roleName);
}
