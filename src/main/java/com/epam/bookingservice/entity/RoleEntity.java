package com.epam.bookingservice.entity;

import java.util.Arrays;
import java.util.Optional;

public enum RoleEntity {

    CLIENT, WORKER, ADMIN;

    public static Optional<RoleEntity> findByName(String name) {
        return Arrays.stream(values())
                .filter(roleEntity -> roleEntity.name().equals(name))
                .findAny();
    }
}
