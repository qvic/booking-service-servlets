package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.entity.ServiceEntity;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServiceDaoImplTest extends AbstractDaoImplTest {

    private static final ServiceEntity TEST_SERVICE = ServiceEntity.builder()
            .setName("Name")
            .setDurationInTimeslots(2)
            .setPrice(100)
            .setWorkspaces(3)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        ServiceDao serviceDao = new ServiceDaoImpl(connector);
        ServiceEntity saved = serviceDao.save(TEST_SERVICE);

        Optional<ServiceEntity> byId = serviceDao.findById(saved.getId());
        assertTrue("Could not fetch service after saving", byId.isPresent());

        ServiceEntity fetched = byId.get();
        assertEquals(saved, fetched);
    }
}