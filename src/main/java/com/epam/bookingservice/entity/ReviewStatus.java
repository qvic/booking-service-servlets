package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum ReviewStatus {

    CREATED(1), APPROVED(2);

    private final Integer id;

    ReviewStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static ReviewStatus getById(int id) {
        for (ReviewStatus reviewStatus : values()) {
            if (reviewStatus.id == id) {
                return reviewStatus;
            }
        }
        throw new NoSuchElementException("Not found ReviewStatus for id " + id);
    }
}
