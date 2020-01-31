package com.epam.bookingservice.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private final Integer id;
    private final LocalDateTime date;
    private final User worker;
    private final User client;
    private final Timeslot timeslot;
    private final OrderStatus status;
    private final List<Service> services;

    private Order(Builder builder) {
        id = builder.id;
        date = builder.date;
        worker = builder.worker;
        client = builder.client;
        timeslot = builder.timeslot;
        status = builder.status;
        services = builder.services;
    }

    public Integer getId() {
        return id;
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

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<Service> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", worker=" + worker +
                ", client=" + client +
                ", timeslot=" + timeslot +
                ", status=" + status +
                ", services=" + services +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Order copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.date = copy.getDate();
        builder.worker = copy.getWorker();
        builder.client = copy.getClient();
        builder.timeslot = copy.getTimeslot();
        builder.status = copy.getStatus();
        builder.services = copy.getServices();
        return builder;
    }


    public static final class Builder {
        private Integer id;
        private LocalDateTime date;
        private User worker;
        private User client;
        private Timeslot timeslot;
        private OrderStatus status;
        private List<Service> services;

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

        public Builder setWorker(User worker) {
            this.worker = worker;
            return this;
        }

        public Builder setClient(User client) {
            this.client = client;
            return this;
        }

        public Builder setTimeslot(Timeslot timeslot) {
            this.timeslot = timeslot;
            return this;
        }

        public Builder setStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder setServices(List<Service> services) {
            this.services = services;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
