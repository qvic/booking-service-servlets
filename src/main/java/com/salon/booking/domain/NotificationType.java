package com.salon.booking.domain;

import com.salon.booking.utility.EnumUtility;

import java.util.Optional;

public enum NotificationType {

    LEAVE_FEEDBACK, FEEDBACK_APPROVED;

    public static Optional<NotificationType> findByName(String name) {
        return EnumUtility.findByName(name, values());
    }
}
