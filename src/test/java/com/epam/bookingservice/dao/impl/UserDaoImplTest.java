package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.entity.RoleEntity;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.entity.UserStatusEntity;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserDaoImplTest extends AbstractDaoImplTest {

    private static final UserEntity TEST_USER = UserEntity.builder()
            .setName("Name")
            .setEmail("Email")
            .setPassword("Password")
            .setRole(RoleEntity.CLIENT)
            .setStatus(UserStatusEntity.ACTIVE)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        UserDao userDao = new UserDaoImpl(connector);
        UserEntity saved = userDao.save(TEST_USER);

        Optional<UserEntity> byId = userDao.findById(saved.getId());
        assertTrue("Could not fetch user after saving", byId.isPresent());

        UserEntity fetchedUser = byId.get();
        assertEquals(saved, fetchedUser);
    }
}