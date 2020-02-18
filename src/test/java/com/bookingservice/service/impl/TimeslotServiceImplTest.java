package com.bookingservice.service.impl;

import com.bookingservice.dao.OrderDao;
import com.bookingservice.dao.ServiceDao;
import com.bookingservice.dao.TimeslotDao;
import com.bookingservice.dao.UserDao;
import com.bookingservice.domain.Order;
import com.bookingservice.domain.Timeslot;
import com.bookingservice.domain.Timetable;
import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.ServiceEntity;
import com.bookingservice.entity.TimeslotEntity;
import com.bookingservice.entity.UserEntity;
import com.bookingservice.mapper.Mapper;
import org.junit.After;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeslotServiceImplTest {

    private static final LocalDate FROM_DATE = LocalDate.of(2020, 2, 4);
    private static final LocalDate TO_DATE = LocalDate.of(2020, 2, 9);

    private static final List<TimeslotEntity> TIMESLOT_ENTITIES = initTimeslotEntities();

    private static final List<Timetable> TIMETABLES = initTimetables();

    @Mock
    private TimeslotDao timeslotDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private ServiceDao serviceDao;

    @Mock
    private UserDao userDao;

    @Mock
    private Mapper<TimeslotEntity, Timeslot> timeslotMapper;

    private TimeslotServiceImpl timeslotService;

    @Before
    public void injectMocks() {
        timeslotService = new TimeslotServiceImpl(timeslotDao, orderDao, serviceDao, userDao, timeslotMapper);
    }

    @After
    public void resetMocks() {
        reset(timeslotDao, orderDao, serviceDao, userDao, timeslotMapper);
    }

    @Test
    public void findAllBetween() {
        when(timeslotMapper.mapEntityToDomain(eq(TIMESLOT_ENTITIES.get(0)))).thenReturn(TIMETABLES.get(1).getRows().get(0));
        when(timeslotMapper.mapEntityToDomain(eq(TIMESLOT_ENTITIES.get(1)))).thenReturn(TIMETABLES.get(1).getRows().get(1));
        when(timeslotMapper.mapEntityToDomain(eq(TIMESLOT_ENTITIES.get(2)))).thenReturn(TIMETABLES.get(3).getRows().get(0));

        when(timeslotDao.findAllBetween(eq(FROM_DATE), eq(TO_DATE))).thenReturn(TIMESLOT_ENTITIES);

        List<Timetable> timetables = timeslotService.findAllBetween(FROM_DATE, TO_DATE);

        assertThat(timetables, equalTo(TIMETABLES));
    }

    private static List<TimeslotEntity> initTimeslotEntities() {
        return Arrays.asList(
                TimeslotEntity.builder()
                        .setId(122)
                        .setDate(LocalDate.of(2020, 2, 5))
                        .build(),
                TimeslotEntity.builder()
                        .setId(123)
                        .setDate(LocalDate.of(2020, 2, 5))
                        .build(),
                TimeslotEntity.builder()
                        .setId(124)
                        .setDate(LocalDate.of(2020, 2, 7))
                        .build()
        );
    }

    private static List<Timetable> initTimetables() {
        return Arrays.asList(
                new Timetable(LocalDate.of(2020, 2, 4), Collections.emptyList()),
                new Timetable(LocalDate.of(2020, 2, 5),
                        Arrays.asList(
                                Timeslot.builder()
                                        .setId(122)
                                        .setDate(LocalDate.of(2020, 2, 5))
                                        .build(),
                                Timeslot.builder()
                                        .setId(123)
                                        .setDate(LocalDate.of(2020, 2, 5))
                                        .build())
                ),
                new Timetable(LocalDate.of(2020, 2, 6), Collections.emptyList()),
                new Timetable(LocalDate.of(2020, 2, 7),
                        Collections.singletonList(Timeslot.builder()
                                .setId(124)
                                .setDate(LocalDate.of(2020, 2, 7))
                                .build())
                ),
                new Timetable(LocalDate.of(2020, 2, 8), Collections.emptyList()));
    }
}