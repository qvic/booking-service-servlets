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

    private Timeslot(Builder builder) {
        id = builder.id;
        fromTime = builder.fromTime;
        toTime = builder.toTime;
        date = builder.date;
        order = builder.order;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Timeslot copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.fromTime = copy.getFromTime();
        builder.toTime = copy.getToTime();
        builder.date = copy.getDate();
        builder.order = copy.getOrder();
        return builder;
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

    public Integer getId() {
        return id;
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

    public static final class Builder {
        private Integer id;
        private LocalTime fromTime;
        private LocalTime toTime;
        private LocalDate date;
        private Order order;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setFromTime(LocalTime fromTime) {
            this.fromTime = fromTime;
            return this;
        }

        public Builder setToTime(LocalTime toTime) {
            this.toTime = toTime;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Timeslot build() {
            return new Timeslot(this);
        }
    }
}
