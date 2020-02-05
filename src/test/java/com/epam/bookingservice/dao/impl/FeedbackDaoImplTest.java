package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.FeedbackDao;
import com.epam.bookingservice.entity.FeedbackEntity;
import com.epam.bookingservice.entity.FeedbackStatusEntity;
import com.epam.bookingservice.entity.UserEntity;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FeedbackDaoImplTest extends AbstractDaoImplTest {

    private static final UserEntity TEST_USER = UserEntity.builder()
            .setId(5)
            .build();

    private static final FeedbackEntity TEST_FEEDBACK = FeedbackEntity.builder()
            .setStatus(FeedbackStatusEntity.CREATED)
            .setWorker(TEST_USER)
            .setText("Text")
            .build();

    @Test
    public void feedbackShouldBeMappedCorrectly() {
        FeedbackDao feedbackDao = new FeedbackDaoImpl(connector);
        FeedbackEntity saved = feedbackDao.save(TEST_FEEDBACK);

        Optional<FeedbackEntity> byId = feedbackDao.findById(saved.getId());
        assertTrue("Could not fetch feedback after saving", byId.isPresent());

        FeedbackEntity fetched = byId.get();
        assertEquals(saved, fetched);
    }
}