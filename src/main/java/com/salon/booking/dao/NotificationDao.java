package com.salon.booking.dao;

import com.salon.booking.entity.NotificationEntity;

import java.util.List;
import java.util.Optional;

public interface NotificationDao extends CrudDao<NotificationEntity> {

    List<NotificationEntity> findAllUnread(Integer userId);

    Optional<NotificationEntity> findByOrderId(Integer orderId);

    List<NotificationEntity> findAllRead(Integer userId);

    void updateAllAsRead(Integer userId);
}
