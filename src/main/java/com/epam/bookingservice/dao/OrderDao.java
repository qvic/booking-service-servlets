package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.Order;

import java.util.List;

public interface OrderDao extends PageableCrudDao<Order> {

    List<Order> findOrdersByClientId(Integer id);

    List<Order> findOrdersByWorkerId(Integer id);
}
