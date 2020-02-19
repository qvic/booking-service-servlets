package com.salon.booking.mapper;

import com.salon.booking.domain.NotificationType;
import com.salon.booking.entity.NotificationTypeEntity;

public class NotificationTypeMapper extends AbstractEnumMapper<NotificationTypeEntity, NotificationType> {

    public NotificationTypeMapper() {
        super(NotificationTypeEntity.values(), NotificationType.values());
    }
}
