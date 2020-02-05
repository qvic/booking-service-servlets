package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum UserStatusEntity {

    ACTIVE(1), DEACTIVATED(2);

    private final Integer id;

    UserStatusEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static UserStatusEntity getById(int id) {
        for (UserStatusEntity userStatus : values()) {
            if (userStatus.id == id) {
                return userStatus;
            }
        }
        throw new NoSuchElementException("Not found UserStatus for id " + id);
    }
}
