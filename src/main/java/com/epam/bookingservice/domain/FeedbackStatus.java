package com.epam.bookingservice.domain;

import java.util.Arrays;
import java.util.Optional;

public enum FeedbackStatus {
    APPROVED, CREATED;

    public static Optional<FeedbackStatus> findByName(String name) {
        return Arrays.stream(values())
                .filter(role -> role.name().equals(name))
                .findAny();
    }
}
