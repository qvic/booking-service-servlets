package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum FeedbackStatus {

    CREATED(1), APPROVED(2);

    private final Integer id;

    FeedbackStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static FeedbackStatus getById(int id) {
        for (FeedbackStatus feedbackStatus : values()) {
            if (feedbackStatus.id == id) {
                return feedbackStatus;
            }
        }
        throw new NoSuchElementException("Not found ReviewStatus for id " + id);
    }
}
