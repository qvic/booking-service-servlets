package com.epam.bookingservice.domain;

import java.util.Objects;

public class Service {

    private final Integer id;
    private final String name;
    private final Integer durationInTimeslots;
    private final Integer price;

    public Service(Integer id, String name, Integer durationInTimeslots, Integer price) {
        this.id = id;
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
}
