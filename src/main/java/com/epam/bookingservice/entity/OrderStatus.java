package com.epam.bookingservice.entity;

import java.util.NoSuchElementException;

public enum OrderStatus {

    CREATED(1), APPROVED(2);

    private final Integer id;

    OrderStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static OrderStatus getById(int id) {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.id == id) {
                return orderStatus;
            }
        }
        throw new NoSuchElementException("Not found OrderStatus for id " + id);
    }
}
