package com.salon.booking.service.impl;

import com.salon.booking.dao.OrderDao;
import com.salon.booking.dao.ServiceDao;
import com.salon.booking.dao.TimeslotDao;
import com.salon.booking.dao.UserDao;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.Timetable;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.TimeslotEntity;
import com.salon.booking.mapper.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private static final List<TimeslotEntity> TIMESLOT_ENTITIES_BY_DAY = initTimeslotEntitiesByDay();
    private static final List<Timeslot> CONSECUTIVE_TIMESLOTS = initConsecutiveTimeslots();

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

        when(timeslotDao.findAllBetweenDatesSorted(eq(FROM_DATE), eq(TO_DATE))).thenReturn(TIMESLOT_ENTITIES);

        List<Timetable> timetables = timeslotService.findAllBetween(FROM_DATE, TO_DATE);

        assertThat(timetables, equalTo(TIMETABLES));
    }

    @Test
    public void findConsecutiveFreeTimeslotsShouldReturnCorrectList() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);
        when(timeslotMapper.mapEntityToDomain(eq(TIMESLOT_ENTITIES_BY_DAY.get(1)))).thenReturn(CONSECUTIVE_TIMESLOTS.get(0));
        when(timeslotMapper.mapEntityToDomain(eq(TIMESLOT_ENTITIES_BY_DAY.get(2)))).thenReturn(CONSECUTIVE_TIMESLOTS.get(1));

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findConsecutiveFreeTimeslots(123);

        assertThat(consecutiveFreeTimeslots, equalTo(CONSECUTIVE_TIMESLOTS));
    }

    @Test
    public void findConsecutiveFreeTimeslotsShouldReturnCorrectListIfTimeslotPredecessorIsEmpty() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);
        when(timeslotMapper.mapEntityToDomain(eq(TIMESLOT_ENTITIES_BY_DAY.get(2)))).thenReturn(CONSECUTIVE_TIMESLOTS.get(1));

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findConsecutiveFreeTimeslots(124);

        assertThat(consecutiveFreeTimeslots, equalTo(Collections.singletonList(CONSECUTIVE_TIMESLOTS.get(1))));
    }

    @Test
    public void findConsecutiveFreeTimeslotsShouldReturnEmptyListIfNoFreePlaces() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findConsecutiveFreeTimeslots(122);

        assertThat(consecutiveFreeTimeslots, equalTo(Collections.emptyList()));
    }

    private static List<TimeslotEntity> initTimeslotEntitiesByDay() {
        return Arrays.asList(
                TimeslotEntity.builder()
                        .setId(122)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(8, 0))
                        .setOrder(OrderEntity.builder().build())
                        .build(),
                TimeslotEntity.builder()
                        .setId(123)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(8, 30))
                        .setOrder(null)
                        .build(),
                TimeslotEntity.builder()
                        .setId(124)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(9, 0))
                        .setOrder(null)
                        .build(),
                TimeslotEntity.builder()
                        .setId(125)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(9, 30))
                        .setOrder(OrderEntity.builder().build())
                        .build(),
                TimeslotEntity.builder()
                        .setId(126)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(10, 0))
                        .setOrder(null)
                        .build()
        );
    }

    private static List<Timeslot> initConsecutiveTimeslots() {
        return Arrays.asList(
                Timeslot.builder()
                        .setId(123)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(8, 30))
                        .build(),
                Timeslot.builder()
                        .setId(124)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(9, 0))
                        .build()
        );
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