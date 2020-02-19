package com.salon.booking.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Timeslot {

    private final Integer id;
    private final LocalTime fromTime;
    private final Duration duration;
    private final LocalDate date;
    private final Order order;

    private Timeslot(Builder builder) {
        id = builder.id;
        fromTime = builder.fromTime;
        duration = builder.duration;
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
        builder.duration = copy.getDuration();
        builder.date = copy.getDate();
        builder.order = copy.getOrder();
        return builder;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public Duration getDuration() {
        return duration;
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
                ", duration=" + duration +
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
                Objects.equals(duration, timeslot.duration) &&
                Objects.equals(date, timeslot.date) &&
                Objects.equals(order, timeslot.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromTime, duration, date, order);
    }

    public static final class Builder {
        private Integer id;
        private LocalTime fromTime;
        private Duration duration;
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

        public Builder setDuration(Duration duration) {
            this.duration = duration;
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
