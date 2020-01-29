package com.epam.app.entity;

import java.time.LocalDateTime;

public class Appointment {

    private Integer id;
    private LocalDateTime date;
    private ServiceType serviceType;
    private User worker;
    private User client;

    private Appointment(Builder builder) {
        id = builder.id;
        date = builder.date;
        serviceType = builder.serviceType;
        worker = builder.worker;
        client = builder.client;
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

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Appointment setId(Integer id) {
        this.id = id;
        return this;
    }

    public Appointment setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Appointment setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public Appointment setWorker(User worker) {
        this.worker = worker;
        return this;
    }

    public Appointment setClient(User client) {
        this.client = client;
        return this;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", time=" + date +
                ", serviceType=" + serviceType +
                ", worker=" + worker +
                ", client=" + client +
                '}';
    }


    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Appointment copy) {
        return new Builder(copy);
    }

    public static final class Builder {
        private Integer id;
        private LocalDateTime date;
        private ServiceType serviceType;
        private User worker;
        private User client;

        private Builder() {
        }

        public Builder(Appointment copy) {
            this.id = copy.getId();
            this.date = copy.getDate();
            this.serviceType = copy.getServiceType();
            this.worker = copy.getWorker();
            this.client = copy.getClient();
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder setServiceType(ServiceType serviceType) {
            this.serviceType = serviceType;
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

        public Appointment build() {
            return new Appointment(this);
        }
    }
}
