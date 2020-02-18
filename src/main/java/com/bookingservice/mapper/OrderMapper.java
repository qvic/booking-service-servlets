package com.bookingservice.mapper;

import com.bookingservice.domain.Order;
import com.bookingservice.domain.Service;
import com.bookingservice.domain.User;
import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.ServiceEntity;
import com.bookingservice.entity.UserEntity;

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