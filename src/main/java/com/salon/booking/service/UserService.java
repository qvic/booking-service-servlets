package com.salon.booking.service;

import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;

public interface UserService {

    Page<User> findAll(PageProperties pageable);

    Page<User> findAllWorkers(PageProperties properties);
}
