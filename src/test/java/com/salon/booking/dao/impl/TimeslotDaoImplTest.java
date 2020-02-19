package com.salon.booking.dao.impl;

import com.salon.booking.entity.DurationEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.TimeslotEntity;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeslotDaoImplTest extends AbstractDaoImplTest {

    private static final LocalDate DATE = LocalDate.of(2020, 2, 2);

    private static final OrderEntity TEST_ORDER = OrderEntity.builder()
            .setId(15)
            .build();

    private static final TimeslotEntity TEST_TIMESLOT = TimeslotEntity.builder()
            .setFromTime(LocalTime.of(10, 0))
            .setDuration(new DurationEntity(1, 30))
            .setOrder(TEST_ORDER)
            .setDate(DATE)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        testDaoMapping(new TimeslotDaoImpl(connector), TEST_TIMESLOT, TimeslotEntity::getId,
                "Could not fetch Timeslot after saving");
    }
}