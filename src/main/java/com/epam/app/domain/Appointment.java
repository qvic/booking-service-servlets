package com.epam.app.domain;

import java.time.LocalDateTime;

public class Appointment {

    private final Integer id;
    private final LocalDateTime time;
    private final ServiceType serviceType;
    private final User worker;
    private final User client;

    private Appointment(Builder builder) {
        id = builder.id;
        time = builder.time;
        serviceType = builder.serviceType;
        worker = builder.worker;
        client = builder.client;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
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

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", time=" + time +
                ", serviceType=" + serviceType +
                ", worker=" + worker +
                ", client=" + client +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer id;
        private LocalDateTime time;
        private ServiceType serviceType;
        private User worker;
        private User client;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setTime(LocalDateTime time) {
            this.time = time;
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
