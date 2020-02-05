package com.epam.bookingservice.domain;

import com.epam.bookingservice.entity.TimeslotEntity;

import java.time.LocalTime;

public class TimetableRow {

    private final LocalTime fromTime;
    private final LocalTime toTime;
    private final User orderOwner;

    public TimetableRow(LocalTime fromTime, LocalTime toTime, User orderOwner) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.orderOwner = orderOwner;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public User getOrderOwner() {
        return orderOwner;
    }

    public static TimetableRow fromTimeslotEntity(TimeslotEntity timeslotEntity) {
        return new TimetableRow(timeslotEntity.getFromTime(), timeslotEntity.getToTime(), null);
    }
}
