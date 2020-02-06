package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.ServiceEntity;
import com.epam.bookingservice.entity.UserEntity;

public class OrderMapper implements Mapper<OrderEntity, Order> {

    private Mapper<UserEntity, User> userMapper;
    private Mapper<ServiceEntity, Service> serviceMapper;

    public OrderMapper(Mapper<UserEntity, User> userMapper, Mapper<ServiceEntity, Service> serviceMapper) {
        this.userMapper = userMapper;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public OrderEntity mapDomainToEntity(Order domain) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order mapEntityToDomain(OrderEntity entity) {
        return new Order(entity.getDate(),
                userMapper.mapEntityToDomain(entity.getWorker()),
                userMapper.mapEntityToDomain(entity.getClient()),
                serviceMapper.mapEntityToDomain(entity.getService()));
    }
}
