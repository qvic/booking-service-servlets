package com.bookingservice.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderEntity {

    private final Integer id;
    private final LocalDateTime date;
    private final UserEntity worker;
    private final UserEntity client;
    private final ServiceEntity service;

    private OrderEntity(Builder builder) {
        id = builder.id;
        date = builder.date;
        worker = builder.worker;
        client = builder.client;
        service = builder.service;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public UserEntity getWorker() {
        return worker;
    }

    public UserEntity getClient() {
        return client;
    }

    public ServiceEntity getService() {
        return service;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", date=" + date +
                ", worker=" + worker +
                ", client=" + client +
                ", service=" + service +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderEntity order = (OrderEntity) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(date, order.date) &&
                Objects.equals(worker, order.worker) &&
                Objects.equals(client, order.client) &&
                Objects.equals(service, order.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, worker, client, service);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(OrderEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.date = copy.getDate();
        builder.worker = copy.getWorker();
        builder.client = copy.getClient();
        builder.service = copy.getService();
        return builder;
    }


    public static final class Builder {
        private Integer id;
        private LocalDateTime date;
        private UserEntity worker;
        private UserEntity client;
        private ServiceEntity service;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder setWorker(UserEntity worker) {
            this.worker = worker;
            return this;
        }

        public Builder setClient(UserEntity client) {
            this.client = client;
            return this;
        }

        public Builder setService(ServiceEntity service) {
            this.service = service;
            return this;
        }

        public OrderEntity build() {
            return new OrderEntity(this);
        }
    }
}
