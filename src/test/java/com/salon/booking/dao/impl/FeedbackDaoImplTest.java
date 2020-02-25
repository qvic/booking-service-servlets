package com.salon.booking.dao.impl;

import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.UserEntity;
import org.junit.Test;

public class FeedbackDaoImplTest extends AbstractDaoImplTest {

    private static final OrderEntity TEST_ORDER = OrderEntity.builder()
            .setId(111)
            .build();

    private static final FeedbackEntity TEST_FEEDBACK = FeedbackEntity.builder()
            .setStatus(FeedbackStatusEntity.CREATED)
            .setOrder(TEST_ORDER)
            .setText("TestText")
            .build();

    @Test
    public void feedbackShouldBeMappedCorrectly() {
        testDaoMapping(new FeedbackDaoImpl(connector), TEST_FEEDBACK, FeedbackEntity::getId,
                "Could not fetch Feedback after saving");
    }
}