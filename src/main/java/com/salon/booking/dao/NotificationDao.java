package com.salon.booking.dao;

import com.salon.booking.entity.NotificationEntity;

import java.util.List;

public interface NotificationDao extends CrudDao<NotificationEntity> {

    List<NotificationEntity> findAllUnread(Integer userId);

    long countUnread(Integer userId);
}
