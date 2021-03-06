package com.salon.booking.dao.impl;

import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.ServiceEntity;
import com.salon.booking.entity.UserEntity;
import org.junit.Test;

import java.time.LocalDateTime;

public class OrderDaoImplTest extends AbstractDaoImplTest {

    private static final ServiceEntity TEST_SERVICE = ServiceEntity.builder()
            .setId(102)
            .build();

    private static final UserEntity TEST_CLIENT = UserEntity.builder()
            .setId(102)
            .build();

    private static final UserEntity TEST_WORKER = UserEntity.builder()
            .setId(101)
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