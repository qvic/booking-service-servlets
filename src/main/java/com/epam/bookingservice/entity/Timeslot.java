package com.epam.bookingservice.entity;

import java.time.LocalTime;

public class Timeslot {

    private final Integer id;
    private final Integer weekday;
    private final LocalTime fromTime;
    private final LocalTime toTime;

    private Timeslot(Builder builder) {
        id = builder.id;
        weekday = builder.weekday;
        fromTime = builder.fromTime;
        toTime = builder.toTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "id=" + id +
                ", weekday=" + weekday +
                ", from=" + fromTime +
                ", to=" + toTime +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Timeslot copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.weekday = copy.getWeekday();
        builder.fromTime = copy.getFromTime();
        builder.toTime = copy.getToTime();
        return builder;
    }

    public static final class Builder {

        private Integer id;
        private Integer weekday;
        private LocalTime fromTime;
        private LocalTime toTime;

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

        public Builder setFromTime(LocalTime from) {
            this.fromTime = from;
            return this;
        }

        public Builder setToTime(LocalTime toTime) {
            this.toTime = toTime;
            return this;
        }

        public Timeslot build() {
            return new Timeslot(this);
        }
    }
}
