package com.salon.booking.dao.impl;

import com.salon.booking.entity.ServiceEntity;
import org.junit.Test;

public class ServiceDaoImplTest extends AbstractDaoImplTest {

    private static final ServiceEntity TEST_SERVICE = ServiceEntity.builder()
            .setName("TestName")
            .setDurationMinutes(30)
            .setPrice(150)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        testDaoMapping(new ServiceDaoImpl(connector), TEST_SERVICE, ServiceEntity::getId,
                "Could not fetch Service after saving");
    }
}