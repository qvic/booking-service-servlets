package com.bookingservice.mapper;

import com.bookingservice.domain.Order;
import com.bookingservice.domain.Timeslot;
import com.bookingservice.entity.DurationEntity;
import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.TimeslotEntity;

import java.time.Duration;

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
                .setDuration(mapDuration(timeslot.getDuration()))
                .setDate(timeslot.getDate())
                .setOrder(orderMapper.mapDomainToEntity(timeslot.getOrder()))
                .build();
    }

    private DurationEntity mapDuration(Duration duration) {
        return (duration == null) ? null :
                new DurationEntity(null, Math.toIntExact(duration.toMinutes()));
    }

    @Override
    public Timeslot mapEntityToDomain(TimeslotEntity timeslot) {
        if (timeslot == null) {
            return null;
        }

        return Timeslot.builder()
                .setId(timeslot.getId())
                .setFromTime(timeslot.getFromTime())
                .setDuration(mapDuration(timeslot.getDuration()))
                .setDate(timeslot.getDate())
                .setOrder(orderMapper.mapEntityToDomain(timeslot.getOrder()))
                .build();
    }

    private Duration mapDuration(DurationEntity durationEntity) {
        return (durationEntity == null) ? null :
                Duration.ofMinutes(durationEntity.getMinutes());
    }
}
