package com.bookingservice.service;

import com.bookingservice.domain.Order;
import com.bookingservice.domain.Service;

import java.util.List;

public interface OrderService {

    List<Order> findAllByClientId(Integer clientId);

    List<Order> findAllByWorkerId(Integer workerId);

    List<Service> findAllServices();

    void saveOrderUpdatingTimeslots(Integer selectedTimeslotId, Order order);
}
