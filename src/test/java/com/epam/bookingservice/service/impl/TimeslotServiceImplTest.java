package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.dao.impl.connector.TransactionManager;
import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.domain.Timetable;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.mapper.OrderMapper;
import com.epam.bookingservice.mapper.ServiceMapper;
import com.epam.bookingservice.mapper.TimeslotMapper;
import com.epam.bookingservice.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeslotServiceImplTest {

    private static final LocalDate FROM_DATE = LocalDate.of(2020, 2, 4);
    private static final LocalDate TO_DATE = LocalDate.of(2020, 2, 9);

    private static final LocalDateTime ORDER_CREATION_DATE = LocalDateTime.of(2020, 1, 1, 12, 0);

    private static final List<OrderEntity> ORDER_ENTITIES = initOrderEntities();

    private static final List<Order> ORDERS = initOrders();

    private static final List<TimeslotEntity> TIMESLOT_ENTITIES = initTimeslotEntities();

    private static final List<Timetable> TIMETABLES = initTimetables();

    private static List<OrderEntity> initOrderEntities() {
        return Arrays.asList(
                OrderEntity.builder()
                        .setId(1)
                        .setDate(ORDER_CREATION_DATE)
                        .build(),

                OrderEntity.builder()
                        .setId(2)
                        .setDate(ORDER_CREATION_DATE)
                        .build(),

                OrderEntity.builder()
                        .setId(3)
                        .setDate(ORDER_CREATION_DATE)
                        .build());
    }

    private static List<Order> initOrders() {
        return Arrays.asList(
                Order.builder()
                        .setDate(ORDER_CREATION_DATE)
                        .build(),

                Order.builder()
                        .setDate(ORDER_CREATION_DATE)
                        .build(),

                Order.builder()
                        .setDate(ORDER_CREATION_DATE)
                        .build());
    }

    private static List<TimeslotEntity> initTimeslotEntities() {
        return Arrays.asList(
                TimeslotEntity.builder()
                        .setId(122)
                        .setOrder(OrderEntity.builder().setId(ORDER_ENTITIES.get(0).getId()).build())
                        .setFromTime(LocalTime.of(8, 0))
                        .setToTime(LocalTime.of(8, 30))
                        .setDate(LocalDate.of(2020, 2, 2))
                        .build(),
                TimeslotEntity.builder()
                        .setId(123)
                        .setOrder(OrderEntity.builder().setId(ORDER_ENTITIES.get(1).getId()).build())
                        .setFromTime(LocalTime.of(8, 30))
                        .setToTime(LocalTime.of(9, 0))
                        .setDate(LocalDate.of(2020, 2, 5))
                        .build(),
                TimeslotEntity.builder()
                        .setId(124)
                        .setOrder(OrderEntity.builder().setId(ORDER_ENTITIES.get(2).getId()).build())
                        .setFromTime(LocalTime.of(9, 30))
                        .setToTime(LocalTime.of(10, 0))
                        .setDate(LocalDate.of(2020, 2, 7))
                        .build()
        );
    }

    private static List<Timetable> initTimetables() {
        return Arrays.asList(
                new Timetable(LocalDate.of(2020, 2, 4), Collections.emptyList()),
                new Timetable(LocalDate.of(2020, 2, 5),
                        Collections.singletonList(new Timeslot(
                                123,
                                LocalTime.of(8, 30),
                                LocalTime.of(9, 0),
                                LocalDate.of(2020, 2, 5),
                                ORDERS.get(0)))
                ),
                new Timetable(LocalDate.of(2020, 2, 6), Collections.emptyList()),
                new Timetable(LocalDate.of(2020, 2, 7),
                        Collections.singletonList(new Timeslot(
                                124,
                                LocalTime.of(9, 30),
                                LocalTime.of(10, 0),
                                LocalDate.of(2020, 2, 7),
                                ORDERS.get(0)))
                ),
                new Timetable(LocalDate.of(2020, 2, 8), Collections.emptyList()));
    }

    @Mock
    private TimeslotDao timeslotDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private TransactionManager transactionManager;

    private Mapper<TimeslotEntity, Timeslot> timeslotMapper = new TimeslotMapper(new OrderMapper(new UserMapper(), new ServiceMapper()));

    private TimeslotServiceImpl timeslotService;

    @Before
    public void injectMocks() {
        timeslotService = new TimeslotServiceImpl(timeslotDao, orderDao, timeslotMapper, transactionManager);
    }

    @Test
    public void findAllBetween() {
        when(timeslotDao.findAllBetween(eq(FROM_DATE), eq(TO_DATE))).thenReturn(TIMESLOT_ENTITIES);
        when(orderDao.findById(eq(2))).thenReturn(Optional.of(ORDER_ENTITIES.get(1)));
        when(orderDao.findById(eq(3))).thenReturn(Optional.of(ORDER_ENTITIES.get(2)));

        List<Timetable> timetables = timeslotService.findAllBetween(FROM_DATE, TO_DATE);

        assertThat(timetables, equalTo(TIMETABLES));
    }
}