package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.entity.FeedbackEntity;
import com.epam.bookingservice.entity.FeedbackStatusEntity;
import com.epam.bookingservice.entity.UserEntity;
import org.junit.Test;

public class FeedbackDaoImplTest extends AbstractDaoImplTest {

    private static final UserEntity TEST_USER = UserEntity.builder()
            .setId(21)
            .build();

    private static final FeedbackEntity TEST_FEEDBACK = FeedbackEntity.builder()
            .setStatus(FeedbackStatusEntity.CREATED)
            .setWorker(TEST_USER)
            .setText("TestText")
            .build();

    @Test
    public void feedbackShouldBeMappedCorrectly() {
        testDaoMapping(new FeedbackDaoImpl(connector), TEST_FEEDBACK, FeedbackEntity::getId,
                "Could not fetch Feedback after saving");
    }
}