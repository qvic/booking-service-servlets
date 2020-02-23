package com.salon.booking.service.impl;

import com.salon.booking.dao.NotificationDao;
import com.salon.booking.domain.Notification;
import com.salon.booking.domain.Order;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.NotificationService;
import com.salon.booking.service.OrderService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class NotificationServiceImpl implements NotificationService {

    private final OrderService orderService;
    private final NotificationDao notificationDao;
    private final Mapper<NotificationEntity, Notification> notificationMapper;

    public NotificationServiceImpl(OrderService orderService, NotificationDao notificationDao, Mapper<NotificationEntity, Notification> notificationMapper) {
        this.orderService = orderService;
        this.notificationDao = notificationDao;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public List<Notification> findAllUnread(Integer userId) {
        return notificationDao.findAllUnread(userId).stream()
                .map(notificationMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countUnread(Integer userId) {
        return notificationDao.countUnread(userId);
    }

    @Override
    public void markAsRead(Integer notificationId) {
        NotificationEntity notification = notificationDao.findById(notificationId)
                .orElseThrow(NoSuchElementException::new);

        notificationDao.update(NotificationEntity.builder(notification)
                .setRead(true)
                .build());
    }

    @Override
    public void save(Notification notification) {
        notificationDao.save(notificationMapper.mapDomainToEntity(notification));
    }
}
