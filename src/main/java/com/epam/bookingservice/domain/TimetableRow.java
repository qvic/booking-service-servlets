package com.epam.bookingservice.domain;

import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;

import java.time.LocalTime;

public class TimetableRow {

    private final LocalTime fromTime;
    private final LocalTime toTime;
    private final OrderEntity order;

    public TimetableRow(LocalTime fromTime, LocalTime toTime, OrderEntity order) {
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

    public static TimetableRow fromTimeslotEntity(TimeslotEntity entity) {
        OrderEntity orderEntity = entity.getOrder();
        return new TimetableRow(entity.getFromTime(), entity.getToTime(), orderEntity);
    }
}
