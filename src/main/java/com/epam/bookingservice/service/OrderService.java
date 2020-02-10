package com.epam.bookingservice.service;

import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.domain.User;

import java.util.List;

public interface OrderService {

    List<Order> findAllByClient(User client);

    List<Order> findAllByWorker(User worker);

    List<Service> findAllServices();
}
