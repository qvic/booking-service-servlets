package com.epam.bookingservice.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Timeslot {

    private final Integer id;
    private final LocalTime fromTime;
    private final LocalTime toTime;
    private final LocalDate date;
    private final Order order;

    public Timeslot(Integer id, LocalTime fromTime, LocalTime toTime, LocalDate date, Order order) {
        this.id = id;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.date = date;
        this.order = order;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "id=" + id +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", date=" + date +
                ", order=" + order +
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
        Timeslot timeslot = (Timeslot) o;
        return Objects.equals(id, timeslot.id) &&
                Objects.equals(fromTime, timeslot.fromTime) &&
                Objects.equals(toTime, timeslot.toTime) &&
                Objects.equals(date, timeslot.date) &&
                Objects.equals(order, timeslot.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromTime, toTime, date, order);
    }
}
