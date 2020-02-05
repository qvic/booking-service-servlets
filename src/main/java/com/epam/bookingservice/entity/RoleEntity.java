package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum RoleEntity {

    CLIENT(1), WORKER(2), ADMIN(3);

    private final Integer id;

    RoleEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static RoleEntity getById(int id) {
        for (RoleEntity role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new NoSuchElementException("Not found Role for id " + id);
    }
}
