package com.bookingservice.entity;

import java.util.Objects;

public class ServiceEntity {

    private final Integer id;
    private final String name;
    private final Integer durationMinutes;
    private final Integer price;
    private final Integer workspaces;

    private ServiceEntity(Builder builder) {
        id = builder.id;
        name = builder.name;
        durationMinutes = builder.durationMinutes;
        price = builder.price;
        workspaces = builder.workspaces;
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

    public Integer getWorkspaces() {
        return workspaces;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", price=" + price +
                ", workspaces=" + workspaces +
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
                Objects.equals(price, service.price) &&
                Objects.equals(workspaces, service.workspaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, durationMinutes, price, workspaces);
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
        builder.workspaces = copy.getWorkspaces();
        return builder;
    }


    public static final class Builder {
        private Integer id;
        private String name;
        private Integer durationMinutes;
        private Integer price;
        private Integer workspaces;

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

        public Builder setWorkspaces(Integer workspaces) {
            this.workspaces = workspaces;
            return this;
        }

        public ServiceEntity build() {
            return new ServiceEntity(this);
        }
    }
}