package com.salon.booking.domain;

import java.time.Duration;
import java.util.Objects;

public class Service {

    private final Integer id;
    private final String name;
    private final Duration duration;
    private final Integer price;
    private final Boolean available;

    private Service(Builder builder) {
        id = builder.id;
        name = builder.name;
        duration = builder.duration;
        price = builder.price;
        available = builder.available;
    }

    public String getName() {
        return name;
    }

    public Duration getDuration() {
        return duration;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", available=" + available +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id) &&
                Objects.equals(name, service.name) &&
                Objects.equals(duration, service.duration) &&
                Objects.equals(price, service.price) &&
                Objects.equals(available, service.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, price, available);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Service copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.duration = copy.getDuration();
        builder.price = copy.getPrice();
        builder.available = copy.getAvailable();
        return builder;
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private Duration duration;
        private Integer price;
        private Boolean available;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder setPrice(Integer price) {
            this.price = price;
            return this;
        }

        public Builder setAvailable(Boolean available) {
            this.available = available;
            return this;
        }

        public Service build() {
            return new Service(this);
        }
    }
}
