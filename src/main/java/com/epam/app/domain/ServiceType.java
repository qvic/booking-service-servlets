package com.epam.app.domain;

import java.time.Duration;

public class ServiceType {

    private final Integer id;
    private final String name;
    private final Duration duration;

    public ServiceType(Integer id, String name, Duration duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
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

    @Override
    public String toString() {
        return "ServiceType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}
