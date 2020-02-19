package com.salon.booking.domain;

import com.salon.booking.utility.EnumUtility;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    CLIENT, ADMIN, WORKER;

    public static Optional<Role> findByName(String name) {
        return EnumUtility.findByName(name, values());
    }
}
