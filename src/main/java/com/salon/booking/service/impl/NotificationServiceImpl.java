package com.salon.booking.service.impl;

import com.salon.booking.dao.NotificationDao;
import com.salon.booking.domain.Notification;
import com.salon.booking.domain.Order;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.NotificationService;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.exception.NoSuchItemException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    public List<Notification> findAllUnreadAndMarkAllAsRead(Integer userId) {
        List<Notification> notifications = notificationDao.findAllUnread(userId).stream()
                .map(this::buildNotificationWithOrder)
                .collect(Collectors.toList());

        notificationDao.updateAllAsRead(userId);

        return notifications;
    }

    @Override
    public List<Notification> findAllRead(Integer userId) {
        return notificationDao.findAllRead(userId).stream()
                .map(this::buildNotificationWithOrder)
                .collect(Collectors.toList());
    }

    private Notification buildNotificationWithOrder(NotificationEntity notificationEntity) {
        Notification notification = notificationMapper.mapEntityToDomain(notificationEntity);

        Integer orderId = notificationEntity.getOrder().getId();
        Order order = orderService.findById(orderId)
                .orElseThrow(NoSuchItemException::new);

        return new Notification(notification.getId(), order, notification.getRead());
    }

    @Override
    public int updateNotificationsReturningCount(Integer userId, LocalDateTime minOrderEndTime) {
        List<Order> lastFinishedOrders = orderService.findFinishedOrdersAfter(minOrderEndTime, userId);

        int unreadNotifications = 0;
        for (Order order : lastFinishedOrders) {
            Optional<NotificationEntity> notification = notificationDao.findByOrderId(order.getId());

            if (notification.isPresent()) {
                if (notification.get().getRead().equals(false)) {
                    unreadNotifications++;
                }
            } else {
                Integer orderId = order.getId();
                notificationDao.save(buildNewNotification(orderId));
                unreadNotifications++;
            }
        }
        return unreadNotifications;
    }

    private NotificationEntity buildNewNotification(Integer orderId) {
        return new NotificationEntity(
                null,
                OrderEntity.builder()
                        .setId(orderId)
                        .build(),
                false);
    }
}
