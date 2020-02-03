package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.entity.Role;
import com.epam.bookingservice.entity.Order;
import com.epam.bookingservice.entity.OrderStatus;
import com.epam.bookingservice.entity.Service;
import com.epam.bookingservice.entity.Timeslot;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.entity.UserStatus;
import com.epam.bookingservice.utility.DatabaseConnector;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderDaoImplTest extends AbstractDaoImplTest {

    private static final Timeslot TEST_TIMESLOT = Timeslot.builder()
            .setFromTime(LocalTime.of(8, 0))
            .setToTime(LocalTime.of(9, 30))
            .setDate(LocalDate.now())
            .build();

    private static final Service TEST_SERVICE = Service.builder()
            .setName("Name")
            .setDurationInTimeslots(2)
            .setPrice(100)
            .setWorkspaces(3)
            .build();

    private static final User TEST_USER = User.builder()
            .setName("Name")
            .setEmail("Email")
            .setPassword("Password")
            .setRole(Role.CLIENT)
            .setStatus(UserStatus.ACTIVE)
            .build();

    private static final Order TEST_ORDER = Order.builder()
            .setDate(LocalDateTime.now())
            .setClient(TEST_USER)
            .setTimeslot(TEST_TIMESLOT)
            .setStatus(OrderStatus.CREATED)
            .setService(TEST_SERVICE)
            .build();

    @Test
    public void userShouldBeMappedCorrectly() {
        DatabaseConnector connector = getConnector();
        initializeDatabase();

        OrderDao orderDao = new OrderDaoImpl(connector);
        Order saved = orderDao.save(TEST_ORDER);

        Optional<Order> byId = orderDao.findById(saved.getId());
        assertTrue("Could not fetch order after saving", byId.isPresent());

        Order fetched = byId.get();
        assertEquals(saved, fetched);
    }
}