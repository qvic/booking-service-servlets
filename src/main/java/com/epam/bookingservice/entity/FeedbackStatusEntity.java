package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum FeedbackStatusEntity {

    CREATED(1), APPROVED(2);

    private final Integer id;

    FeedbackStatusEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static FeedbackStatusEntity getById(int id) {
        for (FeedbackStatusEntity feedbackStatus : values()) {
            if (feedbackStatus.id == id) {
                return feedbackStatus;
            }
        }
        throw new NoSuchElementException("Not found ReviewStatus for id " + id);
    }
}
