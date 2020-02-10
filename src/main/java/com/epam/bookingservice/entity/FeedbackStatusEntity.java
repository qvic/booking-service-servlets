package com.epam.bookingservice.entity;

import java.util.Arrays;
import java.util.Optional;

public enum FeedbackStatusEntity {

    CREATED(1), APPROVED(2);

    private final Integer id;

    FeedbackStatusEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Optional<FeedbackStatusEntity> findByIdAndName(Integer id, String name) {
        return Arrays.stream(values())
                .filter(feedbackStatus -> feedbackStatus.getId().equals(id))
                .filter(feedbackStatus -> feedbackStatus.name().equals(name))
                .findAny();
    }
}
