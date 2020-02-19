package com.salon.booking.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private final LocalDateTime date;
    private final User worker;
    private final User client;
    private final Service service;

    private Order(Builder builder) {
        date = builder.date;
        worker = builder.worker;
        client = builder.client;
        service = builder.service;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Order copy) {
        Builder builder = new Builder();
        builder.date = copy.getDate();
        builder.worker = copy.getWorker();
        builder.client = copy.getClient();
        builder.service = copy.getService();
        return builder;
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

    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                ", worker=" + worker +
                ", client=" + client +
                ", service=" + service +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(date, order.date) &&
                Objects.equals(worker, order.worker) &&
                Objects.equals(client, order.client) &&
                Objects.equals(service, order.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, worker, client, service);
    }

    public static final class Builder {
        private LocalDateTime date;
        private User worker;
        private User client;
        private Service service;

        private Builder() {
        }

        public Builder setDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder setWorker(User worker) {
            this.worker = worker;
            return this;
        }

        public Builder setClient(User client) {
            this.client = client;
            return this;
        }

        public Builder setService(Service service) {
            this.service = service;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
