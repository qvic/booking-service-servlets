package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends PageableCrudDao<Order> {

    List<Order> findAllByClientId(Integer id);

    List<Order> findAllByWorkerId(Integer id);
}
