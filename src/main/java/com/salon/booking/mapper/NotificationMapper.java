package com.salon.booking.mapper;

import com.salon.booking.domain.Notification;
import com.salon.booking.domain.Order;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.OrderEntity;

public class NotificationMapper implements Mapper<NotificationEntity, Notification> {

    private final Mapper<OrderEntity, Order> orderMapper;

    public NotificationMapper(Mapper<OrderEntity, Order> orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public NotificationEntity mapDomainToEntity(Notification notification) {
        if (notification == null) {
            return null;
        }

        return new NotificationEntity(notification.getId(),
                orderMapper.mapDomainToEntity(notification.getOrder()),
                notification.getRead());
    }

    @Override
    public Notification mapEntityToDomain(NotificationEntity notification) {
        if (notification == null) {
            return null;
        }

        return new Notification(notification.getId(),
                orderMapper.mapEntityToDomain(notification.getOrder()),
                notification.getRead());
    }
}
