package com.salon.booking.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class TimeslotEntity {

    private final Integer id;
    private final LocalDate date;
    private final LocalTime fromTime;
    private final OrderEntity order;
    private final DurationEntity duration;

    private TimeslotEntity(Builder builder) {
        id = builder.id;
        date = builder.date;
        fromTime = builder.fromTime;
        duration = builder.duration;
        order = builder.order;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public DurationEntity getDuration() {
        return duration;
    }

    public OrderEntity getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "TimeslotEntity{" +
                "id=" + id +
                ", date=" + date +
                ", fromTime=" + fromTime +
                ", duration=" + duration +
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
        TimeslotEntity timeslot = (TimeslotEntity) o;
        return Objects.equals(id, timeslot.id) &&
                Objects.equals(date, timeslot.date) &&
                Objects.equals(fromTime, timeslot.fromTime) &&
                Objects.equals(duration, timeslot.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, fromTime, duration);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(TimeslotEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.date = copy.getDate();
        builder.fromTime = copy.getFromTime();
        builder.duration = copy.getDuration();
        builder.order = copy.getOrder();
        return builder;
    }

    public static final class Builder {

        private Integer id;
        private LocalDate date;
        private LocalTime fromTime;
        private DurationEntity duration;
        private OrderEntity order;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder setFromTime(LocalTime from) {
            this.fromTime = from;
            return this;
        }

        public Builder setDuration(DurationEntity duration) {
            this.duration = duration;
            return this;
        }

        public Builder setOrder(OrderEntity order) {
            this.order = order;
            return this;
        }

        public TimeslotEntity build() {
            return new TimeslotEntity(this);
        }
    }
}
