package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum Role {

    CLIENT(1), WORKER(2), ADMIN(3);

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
