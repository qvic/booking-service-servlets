package com.bookingservice.mapper;

import com.bookingservice.domain.Role;
import com.bookingservice.entity.RoleEntity;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class RoleMapperTest {

    private RoleMapper roleMapper;

    @Before
    public void before() {
        roleMapper = new RoleMapper();
    }

    @Test
    public void mapDomainToEntityShouldReturnCorrectEnumValue() {
        TestCase.assertEquals(RoleEntity.CLIENT, roleMapper.mapDomainToEntity(Role.CLIENT));
    }

    @Test
    public void mapEntityToDomainShouldReturnCorrectEnumValue() {
        TestCase.assertEquals(Role.CLIENT, roleMapper.mapEntityToDomain(RoleEntity.CLIENT));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        TestCase.assertNull(roleMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        TestCase.assertNull(roleMapper.mapEntityToDomain(null));
    }
}