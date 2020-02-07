package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.OrderStatusEntity;
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
        if (domain == null) {
            return null;
        }

        return OrderEntity.builder()
                .setDate(domain.getDate())
                .setClient(userMapper.mapDomainToEntity(domain.getClient()))
                .setWorker(userMapper.mapDomainToEntity(domain.getWorker()))
                .setStatus(OrderStatusEntity.CREATED)
                .setService(serviceMapper.mapDomainToEntity(domain.getService()))
                .build();
    }

    @Override
    public Order mapEntityToDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        return Order.builder()
                .setDate(entity.getDate())
                .setWorker(userMapper.mapEntityToDomain(entity.getWorker()))
                .setClient(userMapper.mapEntityToDomain(entity.getClient()))
                .setService(serviceMapper.mapEntityToDomain(entity.getService()))
                .build();
    }
}
