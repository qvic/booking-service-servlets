package com.salon.booking.entity;

import com.salon.booking.utility.EnumUtility;

import java.util.Optional;

public enum RoleEntity {

    CLIENT, WORKER, ADMIN;

    public static Optional<RoleEntity> findByName(String name) {
        return EnumUtility.findByName(name, values());
    }
}
