package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum OrderStatusEntity {

    CREATED(1), COMPLETED(2);

    private final Integer id;

    OrderStatusEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static OrderStatusEntity getById(int id) {
        for (OrderStatusEntity orderStatus : values()) {
            if (orderStatus.id == id) {
                return orderStatus;
            }
        }
        throw new NoSuchElementException("Not found OrderStatus for id " + id);
    }
}
