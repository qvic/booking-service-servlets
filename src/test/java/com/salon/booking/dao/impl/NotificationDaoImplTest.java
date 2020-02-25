package com.salon.booking.dao.impl;

import com.salon.booking.domain.Notification;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.OrderEntity;
import org.junit.Test;

public class NotificationDaoImplTest extends AbstractDaoImplTest {

    private static final OrderEntity ORDER = OrderEntity.builder()
            .setId(111)
            .build();

    private static final NotificationEntity TEST_NOTIFICATION = new NotificationEntity(null, ORDER, false);

    @Test
    public void notificationShouldBeMappedCorrectly() {
        testDaoMapping(new NotificationDaoImpl(connector), TEST_NOTIFICATION, NotificationEntity::getId,
                "Could not fetch Notification after saving");
    }
}
