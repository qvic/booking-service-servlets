package com.epam.bookingservice.domain;

public class Service {

    private final String name;
    private final Integer durationInTimeslots;
    private final Integer price;

    public Service(String name, Integer durationInTimeslots, Integer price) {
        this.name = name;
        this.durationInTimeslots = durationInTimeslots;
        this.price = price;
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
}
