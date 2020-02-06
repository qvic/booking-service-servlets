package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.entity.RoleEntity;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.entity.UserStatusEntity;
import org.junit.Test;

public class UserDaoImplTest extends AbstractDaoImplTest {

    private static final UserEntity TEST_USER = UserEntity.builder()
            .setName("TestName")
            .setEmail("TestEmail")
            .setPassword("TestPassword")
            .setRole(RoleEntity.CLIENT)
            .setStatus(UserStatusEntity.ACTIVE)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        testDaoMapping(new UserDaoImpl(connector), TEST_USER, UserEntity::getId,
                "Could not fetch User after saving");
    }
}