package com.bookingservice.entity;

import com.bookingservice.domain.User;

public class NotificationEntity {

    private final Integer id;
    private final User user;
    private final NotificationTypeEntity type;

    public NotificationEntity(Integer id, User user, NotificationTypeEntity type) {
        this.id = id;
        this.user = user;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public NotificationTypeEntity getType() {
        return type;
    }
}
