package com.salon.booking.mapper;

import com.salon.booking.domain.Order;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.entity.DurationEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.TimeslotEntity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeslotMapperTest {

    @Mock
    private Mapper<OrderEntity, Order> orderMapper;

    @InjectMocks
    private TimeslotMapper timeslotMapper;

    @After
    public void resetMocks() {
        reset(orderMapper);
    }

    @Test
    public void mapDomainToEntityShouldMapCorrectly() {
        Order order = Order.builder()
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .build();

        when(orderMapper.mapDomainToEntity(eq(order))).thenReturn(orderEntity);

        Timeslot timeslot = Timeslot.builder()
                .setFromTime(LocalTime.of(12, 0))
                .setDuration(Duration.ofMinutes(30))
                .setDate(LocalDate.of(2020, 2, 2))
                .setOrder(order)
                .setId(1)
                .build();

        TimeslotEntity timeslotEntity = TimeslotEntity.builder()
                .setFromTime(LocalTime.of(12, 0))
                .setDuration(new DurationEntity(null, 30))
                .setDate(LocalDate.of(2020, 2, 2))
                .setOrder(orderEntity)
                .setId(1)
                .build();

        assertEquals(timeslotEntity, timeslotMapper.mapDomainToEntity(timeslot));
    }

    @Test
    public void mapEntityToDomainShouldMapCorrectly() {
        Order order = Order.builder()
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .build();

        when(orderMapper.mapEntityToDomain(eq(orderEntity))).thenReturn(order);

        Timeslot timeslot = Timeslot.builder()
                .setFromTime(LocalTime.of(12, 0))
                .setDuration(Duration.ofMinutes(30))
                .setDate(LocalDate.of(2020, 2, 2))
                .setOrder(order)
                .setId(1)
                .build();

        TimeslotEntity timeslotEntity = TimeslotEntity.builder()
                .setFromTime(LocalTime.of(12, 0))
                .setDuration(new DurationEntity(123, 30))
                .setDate(LocalDate.of(2020, 2, 2))
                .setOrder(orderEntity)
                .setId(1)
                .build();

        assertEquals(timeslot, timeslotMapper.mapEntityToDomain(timeslotEntity));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(timeslotMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        assertNull(timeslotMapper.mapEntityToDomain(null));
    }
}