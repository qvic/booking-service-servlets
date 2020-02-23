package com.salon.booking.mapper;

import com.salon.booking.domain.Notification;
import com.salon.booking.domain.NotificationType;
import com.salon.booking.domain.User;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.NotificationTypeEntity;
import com.salon.booking.entity.UserEntity;

public class NotificationMapper implements Mapper<NotificationEntity, Notification> {

    private final Mapper<UserEntity, User> userMapper;
    private final Mapper<NotificationTypeEntity, NotificationType> notificationTypeMapper;

    public NotificationMapper(Mapper<UserEntity, User> userMapper, Mapper<NotificationTypeEntity, NotificationType> notificationTypeMapper) {
        this.userMapper = userMapper;
        this.notificationTypeMapper = notificationTypeMapper;
    }

    @Override
    public NotificationEntity mapDomainToEntity(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationEntity.builder()
                .setId(notification.getId())
                .setText(notification.getText())
                .setRead(false)
                .setType(notificationTypeMapper.mapDomainToEntity(notification.getType()))
                .setUser(userMapper.mapDomainToEntity(notification.getUser()))
                .build();
    }

    @Override
    public Notification mapEntityToDomain(NotificationEntity notification) {
        if (notification == null) {
            return null;
        }

        return Notification.builder()
                .setId(notification.getId())
                .setText(notification.getText())
                .setType(notificationTypeMapper.mapEntityToDomain(notification.getType()))
                .setUser(userMapper.mapEntityToDomain(notification.getUser()))
                .build();
    }
}
