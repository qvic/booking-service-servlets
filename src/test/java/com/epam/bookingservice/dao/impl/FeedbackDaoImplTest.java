package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.FeedbackDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.entity.Feedback;
import com.epam.bookingservice.entity.FeedbackStatus;
import com.epam.bookingservice.entity.Role;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.entity.UserStatus;
import com.epam.bookingservice.utility.DatabaseConnector;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FeedbackDaoImplTest extends AbstractDaoImplTest {

    private static final User TEST_USER = User.builder()
            .setId(1)
            .setName("Name")
            .setEmail("Email")
            .setPassword("Password")
            .setRole(Role.CLIENT)
            .setStatus(UserStatus.ACTIVE)
            .build();

    private static final Feedback TEST_FEEDBACK = Feedback.builder()
            .setStatus(FeedbackStatus.CREATED)
            .setWorker(TEST_USER)
            .setText("Text")
            .build();

    @Test
    public void feedbackShouldBeMappedCorrectly() {
        DatabaseConnector connector = getConnector();
        initializeDatabase();

        FeedbackDao feedbackDao = new FeedbackDaoImpl(connector);
        Feedback saved = feedbackDao.save(TEST_FEEDBACK);

        Optional<Feedback> byId = feedbackDao.findById(saved.getId());
        assertTrue("Could not fetch feedback after saving", byId.isPresent());

        Feedback fetched = byId.get();
        assertEquals(saved, fetched);
    }
}