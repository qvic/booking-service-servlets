package com.bookingservice.entity;

import com.bookingservice.utility.EnumUtility;

import java.util.Optional;

public enum NotificationTypeEntity {

    LEAVE_FEEDBACK, FEEDBACK_APPROVED;

    public static Optional<NotificationTypeEntity> findByName(String name) {
        return EnumUtility.findByName(name, values());
    }
}
