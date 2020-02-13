package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;

public class TimeslotMapper implements Mapper<TimeslotEntity, Timeslot> {

    private final Mapper<OrderEntity, Order> orderMapper;

    public TimeslotMapper(Mapper<OrderEntity, Order> orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public TimeslotEntity mapDomainToEntity(Timeslot timeslot) {
        if (timeslot == null) {
            return null;
        }

        return TimeslotEntity.builder()
                .setId(timeslot.getId())
                .setFromTime(timeslot.getFromTime())
                .setToTime(timeslot.getToTime())
                .setDate(timeslot.getDate())
                .setOrder(orderMapper.mapDomainToEntity(timeslot.getOrder()))
                .build();
    }

    @Override
    public Timeslot mapEntityToDomain(TimeslotEntity timeslot) {
        if (timeslot == null) {
            return null;
        }

        return Timeslot.builder()
                .setId(timeslot.getId())
                .setFromTime(timeslot.getFromTime())
                .setToTime(timeslot.getToTime())
                .setDate(timeslot.getDate())
                .setOrder(orderMapper.mapEntityToDomain(timeslot.getOrder()))
                .build();
    }
}
