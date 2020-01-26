package com.epam.app.repository;

import com.epam.app.domain.User;

import java.util.Optional;

public interface UserRepository extends PagingRepository<User> {

    Optional<User> findByEmail(String email);
}
