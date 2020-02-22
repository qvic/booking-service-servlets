package com.salon.booking.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeslotEntity {

    private final Integer id;
    private final LocalDate date;
    private final LocalTime fromTime;
    private final List<OrderEntity> orders;
    private final DurationEntity duration;

    private TimeslotEntity(Builder builder) {
        id = builder.id;
        date = builder.date;
        fromTime = builder.fromTime;
        duration = builder.duration;
        orders = builder.orders;
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

    public List<OrderEntity> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "TimeslotEntity{" +
                "id=" + id +
                ", date=" + date +
                ", fromTime=" + fromTime +
                ", duration=" + duration +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeslotEntity that = (TimeslotEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(fromTime, that.fromTime) &&
                Objects.equals(orders, that.orders) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, fromTime, orders, duration);
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
        builder.orders = copy.getOrders();
        return builder;
    }

    public static final class Builder {

        private Integer id;
        private LocalDate date;
        private LocalTime fromTime;
        private DurationEntity duration;
        private List<OrderEntity> orders = new ArrayList<>();

        private Builder() {
        }

        public Builder setOrders(List<OrderEntity> orders) {
            this.orders = orders;
            return this;
        }

        public Builder addOrder(OrderEntity orderEntity) {
            this.orders.add(orderEntity);
            return this;
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

        public TimeslotEntity build() {
            return new TimeslotEntity(this);
        }
    }
}
