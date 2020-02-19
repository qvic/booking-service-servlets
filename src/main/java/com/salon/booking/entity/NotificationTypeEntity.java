package com.salon.booking.entity;

import com.salon.booking.utility.EnumUtility;

import java.util.Optional;

public enum NotificationTypeEntity {

    LEAVE_FEEDBACK, FEEDBACK_APPROVED;

    public static Optional<NotificationTypeEntity> findByName(String name) {
        return EnumUtility.findByName(name, values());
    }
}
