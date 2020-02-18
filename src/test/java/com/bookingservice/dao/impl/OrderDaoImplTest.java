package com.bookingservice.dao.impl;

import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.ServiceEntity;
import com.bookingservice.entity.UserEntity;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderDaoImplTest extends AbstractDaoImplTest {

    private static final ServiceEntity TEST_SERVICE = ServiceEntity.builder()
            .setId(11)
            .build();

    private static final UserEntity TEST_CLIENT = UserEntity.builder()
            .setId(20)
            .build();

    private static final UserEntity TEST_WORKER = UserEntity.builder()
            .setId(21)
            .build();

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2020, 2, 2, 2, 2);

    private static final OrderEntity TEST_ORDER = OrderEntity.builder()
            .setDate(TEST_DATE)
            .setClient(TEST_CLIENT)
            .setWorker(TEST_WORKER)
            .setService(TEST_SERVICE)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        testDaoMapping(new OrderDaoImpl(connector), TEST_ORDER, OrderEntity::getId,
                "Could not fetch Order after saving");
    }
}