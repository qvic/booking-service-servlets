package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum Role {

    ACTIVE(1), DEACTIVATED(2);

    private Integer id;

    Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Role getById(int id) {
        for (Role role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new NoSuchElementException("Not found Role for id " + id);
    }
}
