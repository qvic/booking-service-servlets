package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum UserStatus {

    ACTIVE(1), DEACTIVATED(2);

    private final Integer id;

    UserStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static UserStatus getById(int id) {
        for (UserStatus revieuserStatus : values()) {
            if (revieuserStatus.id == id) {
                return revieuserStatus;
            }
        }
        throw new NoSuchElementException("Not found UserStatus for id " + id);
    }
}
