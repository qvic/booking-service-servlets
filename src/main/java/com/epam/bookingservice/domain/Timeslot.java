package com.epam.bookingservice.domain;

import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;

import java.time.LocalTime;

public class Timeslot {

    private final LocalTime fromTime;
    private final LocalTime toTime;
    // todo Order domain object
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

    public static Timeslot fromTimeslotEntity(TimeslotEntity entity) {
        OrderEntity orderEntity = entity.getOrder();
        return new Timeslot(entity.getFromTime(), entity.getToTime(), orderEntity);
    }
}
