package com.bookingservice.mapper;

import com.bookingservice.domain.Service;
import com.bookingservice.entity.ServiceEntity;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class ServiceMapperTest {

    private ServiceMapper serviceMapper;

    @Before
    public void before() {
        serviceMapper = new ServiceMapper();
    }

    @Test
    public void mapDomainToEntityShouldMapCorrectly() {
        Service service = Service.builder()
                .setId(1)
                .setName("name")
                .setPrice(100)
                .setDuration(Duration.ofMinutes(30))
                .build();

        ServiceEntity expectedService = ServiceEntity.builder()
                .setId(1)
                .setName("name")
                .setPrice(100)
                .setDurationMinutes(30)
                .build();

        assertEquals(expectedService, serviceMapper.mapDomainToEntity(service));
    }

    @Test
    public void mapEntityToDomainShouldMapCorrectly() {
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .setId(1)
                .setName("name")
                .setPrice(100)
                .setDurationMinutes(30)
                .build();

        Service expectedService = Service.builder()
                .setId(1)
                .setName("name")
                .setPrice(100)
                .setDuration(Duration.ofMinutes(30))
                .build();

        TestCase.assertEquals(expectedService, serviceMapper.mapEntityToDomain(serviceEntity));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(serviceMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        TestCase.assertNull(serviceMapper.mapEntityToDomain(null));
    }
}