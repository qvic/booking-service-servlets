package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.entity.Role;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.entity.UserStatus;
import com.epam.bookingservice.utility.DatabaseConnector;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserDaoImplTest extends AbstractDaoImplTest {

    private static final User TEST_USER = User.builder()
            .setName("Name")
            .setEmail("Email")
            .setPassword("Password")
            .setRole(Role.CLIENT)
            .setStatus(UserStatus.ACTIVE)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        DatabaseConnector connector = getConnector();
        initializeDatabase();

        UserDao userDao = new UserDaoImpl(connector);
        User saved = userDao.save(TEST_USER);

        Optional<User> byId = userDao.findById(saved.getId());
        assertTrue("Could not fetch user after saving", byId.isPresent());

        User fetchedUser = byId.get();
        assertEquals(saved, fetchedUser);
    }
}