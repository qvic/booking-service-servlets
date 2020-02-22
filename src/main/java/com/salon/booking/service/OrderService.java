package com.salon.booking.service;

import com.salon.booking.domain.Order;
import com.salon.booking.domain.Service;

import java.util.List;

public interface OrderService {

    List<Order> findAllByClientId(Integer clientId);

    List<Order> findAllByWorkerId(Integer workerId);

    void saveOrderUpdatingTimeslots(Integer selectedTimeslotId, Order order);

    List<Order> findLastFinishedOrders();

    List<Service> findAllServices();
}
