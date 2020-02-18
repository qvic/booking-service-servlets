package com.bookingservice.dao.impl;

import com.bookingservice.entity.ServiceEntity;
import org.junit.Test;

public class ServiceDaoImplTest extends AbstractDaoImplTest {

    private static final ServiceEntity TEST_SERVICE = ServiceEntity.builder()
            .setName("TestName")
            .setDurationMinutes(2)
            .setPrice(150)
            .setWorkspaces(3)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        testDaoMapping(new ServiceDaoImpl(connector), TEST_SERVICE, ServiceEntity::getId,
                "Could not fetch Service after saving");
    }
}