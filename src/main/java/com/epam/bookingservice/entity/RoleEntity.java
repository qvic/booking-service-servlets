package com.epam.bookingservice.entity;

import java.util.Arrays;
import java.util.Optional;

public enum RoleEntity {

    CLIENT(1), WORKER(2), ADMIN(3);

    private final Integer id;

    RoleEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Optional<RoleEntity> findByIdAndName(Integer id, String name) {
        return Arrays.stream(values())
                .filter(roleEntity -> roleEntity.getId().equals(id))
                .filter(roleEntity -> roleEntity.name().equals(name))
                .findAny();
    }   
}
