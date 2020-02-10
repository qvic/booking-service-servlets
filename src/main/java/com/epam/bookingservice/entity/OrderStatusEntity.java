package com.epam.bookingservice.entity;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatusEntity {

    CREATED(1), COMPLETED(2);

    private final Integer id;

    OrderStatusEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Optional<OrderStatusEntity> findByIdAndName(Integer id, String name) {
        return Arrays.stream(values())
                .filter(orderStatus -> orderStatus.getId().equals(id))
                .filter(orderStatus -> orderStatus.name().equals(name))
                .findAny();
    }
}
