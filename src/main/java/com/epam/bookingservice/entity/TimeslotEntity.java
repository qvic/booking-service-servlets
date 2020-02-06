package com.epam.bookingservice.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class TimeslotEntity {

    private final Integer id;
    private final LocalDate date;
    private final LocalTime fromTime;
    private final LocalTime toTime;
    private final OrderEntity order;

    private TimeslotEntity(Builder builder) {
        id = builder.id;
        date = builder.date;
        fromTime = builder.fromTime;
        toTime = builder.toTime;
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

    public LocalTime getToTime() {
        return toTime;
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
                ", toTime=" + toTime +
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
                Objects.equals(toTime, timeslot.toTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, fromTime, toTime);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(TimeslotEntity copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.date = copy.getDate();
        builder.fromTime = copy.getFromTime();
        builder.toTime = copy.getToTime();
        builder.order = copy.getOrder();
        return builder;
    }

    public static final class Builder {

        private Integer id;
        private LocalDate date;
        private LocalTime fromTime;
        private LocalTime toTime;
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

        public Builder setToTime(LocalTime toTime) {
            this.toTime = toTime;
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
