package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.entity.Timeslot;
import com.epam.bookingservice.utility.DatabaseConnector;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeslotDaoImplTest extends AbstractDaoImplTest {

    private static final Timeslot TEST_TIMESLOT = Timeslot.builder()
            .setFromTime(LocalTime.of(8, 0))
            .setToTime(LocalTime.of(9, 30))
            .setDate(LocalDate.now())
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        DatabaseConnector connector = getConnector();
        initializeDatabase();

        TimeslotDao timeslotDao = new TimeslotDaoImpl(connector);
        Timeslot saved = timeslotDao.save(TEST_TIMESLOT);

        Optional<Timeslot> byId = timeslotDao.findById(saved.getId());
        assertTrue("Could not fetch timeslot after saving", byId.isPresent());

        Timeslot fetched = byId.get();
        assertEquals(saved, fetched);
    }
}