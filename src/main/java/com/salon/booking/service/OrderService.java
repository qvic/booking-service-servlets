package com.salon.booking.service;

import com.salon.booking.domain.Order;
import com.salon.booking.domain.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAllByClientId(Integer clientId);

    List<Order> findAllByWorkerId(Integer workerId);

    void saveOrderUpdatingTimeslots(Integer selectedTimeslotId, Order order);

    List<Order> findFinishedOrdersAfter(LocalDateTime dateTime, Integer clientId);

    List<Service> findAllServices();

    Optional<Service> findServiceById(Integer id);

    Optional<Order> findById(Integer id);
}
