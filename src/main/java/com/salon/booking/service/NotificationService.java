package com.salon.booking.service;

import com.salon.booking.domain.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> findAllUnread(Integer userId);

    long countUnread(Integer userId);

    void markAsRead(Integer notificationId);

    void save(Notification notification);
}
