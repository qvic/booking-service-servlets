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
    public TimeslotEntity mapDomainToEntity(Timeslot domain) {
        if (domain == null) {
            return null;
        }

        return TimeslotEntity.builder()
                .setId(domain.getId())
                .setFromTime(domain.getFromTime())
                .setToTime(domain.getToTime())
                .setDate(domain.getDate())
                .setOrder(orderMapper.mapDomainToEntity(domain.getOrder()))
                .build();
    }

    @Override
    public Timeslot mapEntityToDomain(TimeslotEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Timeslot(entity.getId(), entity.getFromTime(), entity.getToTime(),
                entity.getDate(),
                orderMapper.mapEntityToDomain(entity.getOrder()));
    }
}
