package com.epam.bookingservice.entity;

import java.time.LocalTime;

public class Timeslot {

    private final Integer id;
    private final Integer weekday;
    private final LocalTime from;
    private final LocalTime to;

    private Timeslot(Builder builder) {
        id = builder.id;
        weekday = builder.weekday;
        from = builder.from;
        to = builder.to;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "id=" + id +
                ", weekday=" + weekday +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Timeslot copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.weekday = copy.getWeekday();
        builder.from = copy.getFrom();
        builder.to = copy.getTo();
        return builder;
    }

    public static final class Builder {

        private Integer id;
        private Integer weekday;
        private LocalTime from;
        private LocalTime to;

        private Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setWeekday(Integer weekday) {
            this.weekday = weekday;
            return this;
        }

        public Builder setFrom(LocalTime from) {
            this.from = from;
            return this;
        }

        public Builder setTo(LocalTime to) {
            this.to = to;
            return this;
        }

        public Timeslot build() {
            return new Timeslot(this);
        }
    }
}
