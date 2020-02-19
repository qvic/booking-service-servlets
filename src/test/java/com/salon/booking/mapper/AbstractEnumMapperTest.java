package com.salon.booking.mapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractEnumMapperTest {

    private enum TestDomain {
        A, B, D
    }

    private enum TestEntity {
        A, B, C
    }

    private static class TestMapper extends AbstractEnumMapper<TestEntity, TestDomain> {

        TestMapper() {
            super(TestEntity.values(), TestDomain.values());
        }
    }

    private Mapper<TestEntity, TestDomain> testMapper;

    @Before
    public void before() {
        testMapper = new TestMapper();
    }

    @Test
    public void mapDomainToEntityShouldMapCorrectly() {
        assertEquals(TestEntity.A, testMapper.mapDomainToEntity(TestDomain.A));
    }

    @Test
    public void mapDomainToEntityShouldMapInvalidValuesToNull() {
        assertNull(testMapper.mapDomainToEntity(TestDomain.D));
    }

    @Test
    public void mapEntityToDomain() {
        assertEquals(TestDomain.A, testMapper.mapEntityToDomain(TestEntity.A));
    }

    @Test
    public void mapEntityToDomainShouldMapInvalidValuesToNull() {
        assertNull(testMapper.mapEntityToDomain(TestEntity.C));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(testMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        assertNull(testMapper.mapEntityToDomain(null));
    }
}