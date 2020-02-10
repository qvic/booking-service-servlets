package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.ServiceEntity;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ServiceDao serviceDao;
    private final UserDao userDao;

    private final Mapper<OrderEntity, Order> orderMapper;
    private final Mapper<ServiceEntity, Service> serviceMapper;

    public OrderServiceImpl(OrderDao orderDao, ServiceDao serviceDao, UserDao userDao, Mapper<OrderEntity, Order> orderMapper, Mapper<ServiceEntity, Service> serviceMapper) {
        this.orderDao = orderDao;
        this.serviceDao = serviceDao;
        this.userDao = userDao;
        this.orderMapper = orderMapper;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public List<Order> findAllByClient(User client) {
        return orderDao.findAllByClientId(client.getId())
                .stream()
                .map(this::buildWithServiceAndWorker)
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    private OrderEntity buildWithServiceAndWorker(OrderEntity orderEntity) {
        ServiceEntity serviceById = serviceDao.findById(orderEntity.getService().getId())
                .orElseThrow(() -> new RuntimeException("Service id mapped to Order is not present in data source"));
        UserEntity workerById = userDao.findById(orderEntity.getWorker().getId())
                .orElseThrow(() -> new RuntimeException("Worker id mapped to Order is not present in data source"));

        return OrderEntity.builder(orderEntity)
                .setService(serviceById)
                .setWorker(workerById)
                .build();
    }

    @Override
    public List<Order> findAllByWorker(User worker) {
        return orderDao.findAllByWorkerId(worker.getId())
                .stream()
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findAllServices() {
        return serviceDao.findAll()
                .stream()
                .map(serviceMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }
}
