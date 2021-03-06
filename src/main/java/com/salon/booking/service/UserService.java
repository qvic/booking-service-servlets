package com.salon.booking.service;

import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;

import java.util.Optional;

public interface UserService {

    Page<User> findAllWorkers(PageProperties properties);

    Page<User> findAllClients(PageProperties properties);

    void promoteToWorker(Integer clientId);

    Optional<User> findById(Integer id);
}
