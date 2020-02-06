package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.domain.Timetable;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;
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

    private static final OrderEntity FULL_ORDER_1 = OrderEntity.builder()
            .setId(1)
            .setDate(ORDER_CREATION_DATE)
            .build();

    private static final OrderEntity FULL_ORDER_2 = OrderEntity.builder()
            .setId(2)
            .setDate(ORDER_CREATION_DATE)
            .build();

    private static final OrderEntity FULL_ORDER_3 = OrderEntity.builder()
            .setId(3)
            .setDate(ORDER_CREATION_DATE)
            .build();

    private static final List<TimeslotEntity> TIMESLOT_ENTITIES = Arrays.asList(
            TimeslotEntity.builder()
                    .setId(1)
                    .setOrder(OrderEntity.builder().setId(FULL_ORDER_1.getId()).build())
                    .setFromTime(LocalTime.of(8, 0))
                    .setToTime(LocalTime.of(8, 30))
                    .setDate(LocalDate.of(2020, 2, 2))
                    .build(),
            TimeslotEntity.builder()
                    .setId(2)
                    .setOrder(OrderEntity.builder().setId(FULL_ORDER_2.getId()).build())
                    .setFromTime(LocalTime.of(8, 30))
                    .setToTime(LocalTime.of(9, 0))
                    .setDate(LocalDate.of(2020, 2, 5))
                    .build(),
            TimeslotEntity.builder()
                    .setId(3)
                    .setOrder(OrderEntity.builder().setId(FULL_ORDER_3.getId()).build())
                    .setFromTime(LocalTime.of(9, 30))
                    .setToTime(LocalTime.of(10, 0))
                    .setDate(LocalDate.of(2020, 2, 7))
                    .build()
    );

    private static final List<Timetable> TIMETABLES = Arrays.asList(
            new Timetable(LocalDate.of(2020, 2, 4), Collections.emptyList()),
            new Timetable(LocalDate.of(2020, 2, 5),
                    Collections.singletonList(new Timeslot(
                            LocalTime.of(8, 30),
                            LocalTime.of(9, 0),
                            FULL_ORDER_2))
            ),
            new Timetable(LocalDate.of(2020, 2, 6), Collections.emptyList()),
            new Timetable(LocalDate.of(2020, 2, 7),
                    Collections.singletonList(new Timeslot(
                            LocalTime.of(9, 30),
                            LocalTime.of(10, 0),
                            FULL_ORDER_3))
            ),
            new Timetable(LocalDate.of(2020, 2, 8), Collections.emptyList()));

    @Mock
    private TimeslotDao timeslotDao;

    @Mock
    private OrderDao orderDao;

    private TimeslotServiceImpl timeslotService;

    @Before
    public void injectMocks() {
        timeslotService = new TimeslotServiceImpl(timeslotDao, orderDao);
    }

    @Test
    public void findAllBetween() {
        when(timeslotDao.findAllBetween(eq(FROM_DATE), eq(TO_DATE))).thenReturn(TIMESLOT_ENTITIES);
        when(orderDao.findById(eq(2))).thenReturn(Optional.of(FULL_ORDER_2));
        when(orderDao.findById(eq(3))).thenReturn(Optional.of(FULL_ORDER_3));

        List<Timetable> timetables = timeslotService.findAllBetween(FROM_DATE, TO_DATE);

        assertThat(timetables, equalTo(TIMETABLES));
    }
}