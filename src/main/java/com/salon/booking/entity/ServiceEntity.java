package com.salon.booking.entity;

import java.util.Objects;

public class ServiceEntity {

    private final Integer id;
    private final String name;
    private final Integer durationMinutes;
    private final Integer price;

    private ServiceEntity(Builder builder) {
        id = builder.id;
        name = builder.name;
        durationMinutes = builder.durationMinutes;
        price = builder.price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", durationMinutes=" + durationMinutes +
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
        ServiceEntity service = (ServiceEntity) o;
        return Objects.equals(id, service.id) &&
                Objects.equals(name, service.name) &&
                Objects.equals(durationMinutes, service.durationMinutes) &&
                Objects.equals(price, service.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, durationMinutes, price);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(ServiceEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.durationMinutes = copy.getDurationMinutes();
        builder.price = copy.getPrice();
        return builder;
    }


    public static final class Builder {
        private Integer id;
        private String name;
        private Integer durationMinutes;
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

        public Builder setDurationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public Builder setPrice(Integer price) {
            this.price = price;
            return this;
        }

        public ServiceEntity build() {
            return new ServiceEntity(this);
        }
    }
}
