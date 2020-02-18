package com.bookingservice.service;

import com.bookingservice.domain.User;
import com.bookingservice.domain.page.Page;
import com.bookingservice.domain.page.PageProperties;

public interface UserService {

    Page<User> findAll(PageProperties pageable);

    Page<User> findAllWorkers(PageProperties properties);
}
