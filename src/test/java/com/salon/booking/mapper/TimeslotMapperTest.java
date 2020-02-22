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
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeslotMapperTest {

    private static final Order ORDER = Order.builder().build();
    private static final List<Order> ORDERS = Collections.singletonList(ORDER);
    private static final Timeslot TIMESLOT = initTimeslot();

    private static final OrderEntity ORDER_ENTITY = OrderEntity.builder().build();

    private static final List<OrderEntity> ORDER_ENTITIES = Collections.singletonList(ORDER_ENTITY);
    private static final TimeslotEntity TIMESLOT_ENTITY = initTimeslotEntity();

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
        when(orderMapper.mapDomainToEntity(eq(ORDER))).thenReturn(ORDER_ENTITY);

        assertEquals(TIMESLOT_ENTITY, timeslotMapper.mapDomainToEntity(TIMESLOT));
    }

    @Test
    public void mapEntityToDomainShouldMapCorrectly() {
        when(orderMapper.mapEntityToDomain(eq(ORDER_ENTITY))).thenReturn(ORDER);

        assertEquals(TIMESLOT, timeslotMapper.mapEntityToDomain(TIMESLOT_ENTITY));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(timeslotMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        assertNull(timeslotMapper.mapEntityToDomain(null));
    }

    private static Timeslot initTimeslot() {
        return Timeslot.builder()
                .setFromTime(LocalTime.of(12, 0))
                .setDuration(Duration.ofMinutes(30))
                .setDate(LocalDate.of(2020, 2, 2))
                .setOrders(ORDERS)
                .setId(1)
                .build();
    }

    private static TimeslotEntity initTimeslotEntity() {
        return TimeslotEntity.builder()
                .setFromTime(LocalTime.of(12, 0))
                .setDuration(new DurationEntity(null, 30))
                .setDate(LocalDate.of(2020, 2, 2))
                .setOrders(ORDER_ENTITIES)
                .setId(1)
                .build();
    }
}