package com.bookingservice.entity;

import com.bookingservice.utility.EnumUtility;

import java.util.Optional;

public enum RoleEntity {

    CLIENT, WORKER, ADMIN;

    public static Optional<RoleEntity> findByName(String name) {
        return EnumUtility.findByName(name, values());
    }
}
