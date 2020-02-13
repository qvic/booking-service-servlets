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
    public OrderEntity mapDomainToEntity(Order order) {
        if (order == null) {
            return null;
        }

        return OrderEntity.builder()
                .setDate(order.getDate())
                .setClient(userMapper.mapDomainToEntity(order.getClient()))
                .setWorker(userMapper.mapDomainToEntity(order.getWorker()))
                .setService(serviceMapper.mapDomainToEntity(order.getService()))
                .build();
    }

    @Override
    public Order mapEntityToDomain(OrderEntity order) {
        if (order == null) {
            return null;
        }

        return Order.builder()
                .setDate(order.getDate())
                .setWorker(userMapper.mapEntityToDomain(order.getWorker()))
                .setClient(userMapper.mapEntityToDomain(order.getClient()))
                .setService(serviceMapper.mapEntityToDomain(order.getService()))
                .build();
    }
}
