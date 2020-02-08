package com.epam.bookingservice.service;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    private final Mapper<OrderEntity, Order> orderMapper;

    public OrderServiceImpl(OrderDao orderDao, Mapper<OrderEntity, Order> orderMapper) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<Order> findAllByClient(User client) {
        return orderDao.findAllByClientId(client.getId())
                .stream()
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAllByWorker(User worker) {
        return orderDao.findAllByWorkerId(worker.getId())
                .stream()
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }
}
