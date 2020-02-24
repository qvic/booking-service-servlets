package com.salon.booking.domain;

import com.salon.booking.utility.EnumUtility;

import java.util.Optional;

public enum FeedbackStatus {
    
    APPROVED, CREATED;

    public static Optional<FeedbackStatus> findByName(String name) {
        return EnumUtility.findByName(name, values());
    }
}
