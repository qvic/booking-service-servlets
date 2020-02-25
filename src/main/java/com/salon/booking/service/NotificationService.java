package com.salon.booking.service;

import com.salon.booking.domain.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {

    List<Notification> findAllUnreadAndMarkAllAsRead(Integer userId);

    List<Notification> findAllRead(Integer userId);

    int updateNotificationsReturningCount(Integer userId, LocalDateTime currentTime);
}
