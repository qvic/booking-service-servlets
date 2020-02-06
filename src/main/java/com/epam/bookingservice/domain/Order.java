package com.epam.bookingservice.domain;

import java.time.LocalDateTime;

public class Order {

    private final LocalDateTime date;
    private final User worker;
    private final User client;
    private final Service service;

    public Order(LocalDateTime date, User worker, User client, Service service) {
        this.date = date;
        this.worker = worker;
        this.client = client;
        this.service = service;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public User getWorker() {
        return worker;
    }

    public User getClient() {
        return client;
    }

    public Service getService() {
        return service;
    }
}
