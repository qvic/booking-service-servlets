package com.epam.bookingservice.entity;

public class Service {

    private final Integer id;
    private final String name;
    private final Integer durationInTimeslots;
    private final Integer price;
    private final Integer workspaces;

    private Service(Builder builder) {
        id = builder.id;
        name = builder.name;
        durationInTimeslots = builder.durationInTimeslots;
        price = builder.price;
        workspaces = builder.workspaces;
    }

    public Integer getId() {
        return id;
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

    public Integer getWorkspaces() {
        return workspaces;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", durationInTimeslots=" + durationInTimeslots +
                ", price=" + price +
                ", workspaces=" + workspaces +
                '}';
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
        builder.workspaces = copy.getWorkspaces();
        return builder;
    }


    public static final class Builder {
        private Integer id;
        private String name;
        private Integer durationInTimeslots;
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

        public Builder setDurationInTimeslots(Integer durationInTimeslots) {
            this.durationInTimeslots = durationInTimeslots;
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

        public Service build() {
            return new Service(this);
        }
    }
}
