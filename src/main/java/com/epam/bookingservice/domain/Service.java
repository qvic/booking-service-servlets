package com.epam.bookingservice.domain;

import java.util.Objects;

public class Service {

    private final Integer id;
    private final String name;
    private final Integer durationInTimeslots;
    private final Integer price;

    private Service(Builder builder) {
        id = builder.id;
        name = builder.name;
        durationInTimeslots = builder.durationInTimeslots;
        price = builder.price;
    }

    public String getName() {
        return name;
    }

    public Integer getDurationInTimeslots() {
        return durationInTimeslots;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", durationInTimeslots=" + durationInTimeslots +
                ", price=" + price +
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
        Service service = (Service) o;
        return Objects.equals(id, service.id) &&
                Objects.equals(name, service.name) &&
                Objects.equals(durationInTimeslots, service.durationInTimeslots) &&
                Objects.equals(price, service.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, durationInTimeslots, price);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Service copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.durationInTimeslots = copy.getDurationInTimeslots();
        builder.price = copy.getPrice();
        return builder;
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private Integer durationInTimeslots;
        private Integer price;

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

        public Builder setDurationInTimeslots(Integer durationInTimeslots) {
            this.durationInTimeslots = durationInTimeslots;
            return this;
        }

        public Builder setPrice(Integer price) {
            this.price = price;
            return this;
        }

        public Service build() {
            return new Service(this);
        }
    }
}
