package com.epam.bookingservice.mapper;

import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;

public class TimeslotMapper implements Mapper<TimeslotEntity, Timeslot> {

    @Override
    public TimeslotEntity mapDomainToEntity(Timeslot domain) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Timeslot mapEntityToDomain(TimeslotEntity entity) {
        OrderEntity orderEntity = entity.getOrder();
        return new Timeslot(entity.getFromTime(), entity.getToTime(), orderEntity);
    }
}
