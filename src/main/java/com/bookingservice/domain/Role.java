package com.bookingservice.domain;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    CLIENT, ADMIN, WORKER;

    public static Optional<Role> findByName(String name) {
        return Arrays.stream(values())
                .filter(role -> role.name().equals(name))
                .findAny();
    }
}
