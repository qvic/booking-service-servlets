package com.epam.app.entity;

import java.time.Duration;

public class ServiceType {

    private Integer id;
    private String name;
    private Duration duration;
    private Integer availableWorkplaces;

    private ServiceType(Builder builder) {
        id = builder.id;
        name = builder.name;
        duration = builder.duration;
        availableWorkplaces = builder.availableWorkplaces;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Duration getDuration() {
        return duration;
    }

    public Integer getAvailableWorkplaces() {
        return availableWorkplaces;
    }

    public ServiceType setId(Integer id) {
        this.id = id;
        return this;
    }

    public ServiceType setName(String name) {
        this.name = name;
        return this;
    }

    public ServiceType setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public ServiceType setAvailableWorkplaces(Integer availableWorkplaces) {
        this.availableWorkplaces = availableWorkplaces;
        return this;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", availableWorkspaces=" + availableWorkplaces +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private Duration duration;
        private Integer availableWorkplaces;

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

        public Builder setAvailableWorkplaces(Integer availableWorkplaces) {
            this.availableWorkplaces = availableWorkplaces;
            return this;
        }

        public ServiceType build() {
            return new ServiceType(this);
        }
    }
}
