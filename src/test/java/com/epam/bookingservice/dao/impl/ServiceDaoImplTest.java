package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.entity.Service;
import com.epam.bookingservice.utility.DatabaseConnector;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServiceDaoImplTest extends AbstractDaoImplTest {

    private static final Service TEST_SERVICE = Service.builder()
            .setName("Name")
            .setDurationInTimeslots(2)
            .setPrice(100)
            .setWorkspaces(3)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        DatabaseConnector connector = getConnector();
        initializeDatabase();

        ServiceDao serviceDao = new ServiceDaoImpl(connector);
        Service saved = serviceDao.save(TEST_SERVICE);

        Optional<Service> byId = serviceDao.findById(saved.getId());
        assertTrue("Could not fetch service after saving", byId.isPresent());

        Service fetched = byId.get();
        assertEquals(saved, fetched);
    }
}