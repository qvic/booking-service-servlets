package com.epam.bookingservice.domain;

import com.epam.bookingservice.entity.OrderEntity;

import java.time.LocalTime;
import java.util.Objects;

public class Timeslot {

    private final LocalTime fromTime;
    private final LocalTime toTime;
    private final OrderEntity order;

    public Timeslot(LocalTime fromTime, LocalTime toTime, OrderEntity order) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.order = order;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public OrderEntity getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", order=" + order +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return Objects.equals(fromTime, timeslot.fromTime) &&
                Objects.equals(toTime, timeslot.toTime) &&
                Objects.equals(order, timeslot.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromTime, toTime, order);
    }
}
