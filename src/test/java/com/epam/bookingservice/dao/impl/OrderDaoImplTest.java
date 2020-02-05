package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.OrderStatusEntity;
import com.epam.bookingservice.entity.ServiceEntity;
import com.epam.bookingservice.entity.TimeslotEntity;
import com.epam.bookingservice.entity.UserEntity;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderDaoImplTest extends AbstractDaoImplTest {

    private static final TimeslotEntity TEST_TIMESLOT = TimeslotEntity.builder()
            .setId(7)
            .build();

    private static final ServiceEntity TEST_SERVICE = ServiceEntity.builder()
            .setId(5)
            .build();

    private static final UserEntity TEST_CLIENT = UserEntity.builder()
            .setId(5)
            .build();

    private static final UserEntity TEST_WORKER = UserEntity.builder()
            .setId(7)
            .build();

    private static final OrderEntity TEST_ORDER = OrderEntity.builder()
            .setDate(LocalDateTime.now())
            .setStatus(OrderStatusEntity.CREATED)
            .setClient(TEST_CLIENT)
            .setWorker(TEST_WORKER)
            .setTimeslot(TEST_TIMESLOT)
            .setService(TEST_SERVICE)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        OrderDao orderDao = new OrderDaoImpl(connector);
        OrderEntity saved = orderDao.save(TEST_ORDER);

        Optional<OrderEntity> byId = orderDao.findById(saved.getId());
        assertTrue("Could not fetch order after saving", byId.isPresent());

        OrderEntity fetched = byId.get();
        assertEquals(saved, fetched);
    }
}