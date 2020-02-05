package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.entity.TimeslotEntity;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeslotDaoImplTest extends AbstractDaoImplTest {

    private static final TimeslotEntity TEST_TIMESLOT = TimeslotEntity.builder()
            .setFromTime(LocalTime.of(8, 0))
            .setToTime(LocalTime.of(9, 30))
            .setDate(LocalDate.now())
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        TimeslotDao timeslotDao = new TimeslotDaoImpl(connector);
        TimeslotEntity saved = timeslotDao.save(TEST_TIMESLOT);

        Optional<TimeslotEntity> byId = timeslotDao.findById(saved.getId());
        assertTrue("Could not fetch timeslot after saving", byId.isPresent());

        TimeslotEntity fetched = byId.get();
        assertEquals(saved, fetched);
    }
}