package com.salon.booking.service.impl;

import com.salon.booking.dao.OrderDao;
import com.salon.booking.dao.ServiceDao;
import com.salon.booking.dao.TimeslotDao;
import com.salon.booking.dao.UserDao;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Service;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.Timetable;
import com.salon.booking.domain.User;
import com.salon.booking.entity.DurationEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.ServiceEntity;
import com.salon.booking.entity.TimeslotEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeslotServiceImplTest {

    private static final LocalDate FROM_DATE = LocalDate.of(2020, 2, 4);
    private static final LocalDate TO_DATE = LocalDate.of(2020, 2, 9);

    private static final List<OrderEntity> ORDER_ENTITIES = initOrderEntities();

    private static final List<TimeslotEntity> TIMESLOT_ENTITIES = initTimeslotEntities();
    private static final List<Timetable> TIMETABLES = initTimetables();
    private static final List<TimeslotEntity> TIMESLOT_ENTITIES_BY_DAY = initTimeslotEntitiesByDay();


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
    public void findAllBetweenShouldReturnCorrectList() {
        when(serviceDao.count()).thenReturn(2L);
        when(serviceDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(ServiceEntity.builder().setId(invocation.getArgument(0)).build()));
        when(userDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(UserEntity.builder().setId(invocation.getArgument(0)).build()));
        when(orderDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(ORDER_ENTITIES.get(0)));

        when(timeslotMapper.mapEntityToDomain(any())).thenAnswer(invocation -> {
            TimeslotEntity entity = invocation.getArgument(0, TimeslotEntity.class);
            return Timeslot.builder()
                    .setId(entity.getId())
                    .setDate(entity.getDate())
                    .setOrders(mapTestOrderEntities(entity.getOrders()))
                    .build();
        });

        when(timeslotDao.findAllBetweenDatesSorted(eq(FROM_DATE), eq(TO_DATE))).thenReturn(TIMESLOT_ENTITIES);

        List<Timetable> timetables = timeslotService.findAllBetween(FROM_DATE, TO_DATE);

        assertThat(timetables, equalTo(TIMETABLES));
    }

    private static List<Order> mapTestOrderEntities(List<OrderEntity> orderEntities) {
        return orderEntities.stream()
                .map(orderEntity -> Order.builder().build())
                .collect(Collectors.toList());
    }

    @Test
    public void findFreeTimeslotsForServiceWithWorkerShouldReturnEmptyListWhenWorkerIsBusy() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);
        when(orderDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(ORDER_ENTITIES.get(invocation.getArgument(0, Integer.class))));

        when(timeslotMapper.mapEntityToDomain(any())).thenAnswer(
                invocation -> Timeslot.builder()
                        .setId(invocation.getArgument(0, TimeslotEntity.class).getId())
                        .build());

        Service service = Service.builder()
                .setId(2)
                .setDuration(Duration.ofMinutes(30))
                .build();
        User worker = User.builder()
                .setId(1)
                .build();

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findTimeslotsForServiceWithWorker(
                122, service, worker);

        assertThat(consecutiveFreeTimeslots, equalTo(Collections.emptyList()));
    }

    @Test
    public void findFreeTimeslotsForServiceWithWorkerShouldReturnEmptyListWhenServiceIsUnavailable() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);
        when(orderDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(ORDER_ENTITIES.get(invocation.getArgument(0, Integer.class))));

        when(timeslotMapper.mapEntityToDomain(any())).thenAnswer(
                invocation -> Timeslot.builder()
                        .setId(invocation.getArgument(0, TimeslotEntity.class).getId())
                        .build());

        Service service = Service.builder()
                .setId(1)
                .setDuration(Duration.ofMinutes(30))
                .build();
        User worker = User.builder()
                .setId(2)
                .build();

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findTimeslotsForServiceWithWorker(
                122, service, worker);

        assertThat(consecutiveFreeTimeslots, equalTo(Collections.emptyList()));
    }

    @Test
    public void findFreeTimeslotsForServiceWithWorkerShouldReturnCorrectListIfServiceAndWorkerAreAvailable() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);
        when(orderDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(ORDER_ENTITIES.get(invocation.getArgument(0, Integer.class))));

        when(timeslotMapper.mapEntityToDomain(any())).thenAnswer(
                invocation -> Timeslot.builder()
                        .setId(invocation.getArgument(0, TimeslotEntity.class).getId())
                        .build());

        Service service = Service.builder()
                .setId(3)
                .setDuration(Duration.ofMinutes(30))
                .build();
        User worker = User.builder()
                .setId(3)
                .build();

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findTimeslotsForServiceWithWorker(
                122, service, worker);

        List<Integer> timeslotIds = consecutiveFreeTimeslots.stream()
                .map(Timeslot::getId)
                .collect(Collectors.toList());

        assertThat(timeslotIds, equalTo(Collections.singletonList(122)));
    }

    @Test
    public void findFreeTimeslotsForServiceWithWorkerShouldReturnEmptyListIfServiceIntersectsOther() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);
        when(orderDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(ORDER_ENTITIES.get(invocation.getArgument(0, Integer.class))));

        when(timeslotMapper.mapEntityToDomain(any())).thenAnswer(
                invocation -> Timeslot.builder()
                        .setId(invocation.getArgument(0, TimeslotEntity.class).getId())
                        .build());

        Service service = Service.builder()
                .setId(3)
                .setDuration(Duration.ofMinutes(90))
                .build();
        User worker = User.builder()
                .setId(3)
                .build();

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findTimeslotsForServiceWithWorker(
                122, service, worker);

        List<Integer> timeslotIds = consecutiveFreeTimeslots.stream()
                .map(Timeslot::getId)
                .collect(Collectors.toList());

        assertThat(timeslotIds, equalTo(Collections.emptyList()));
    }

    @Test
    public void findFreeTimeslotsForServiceWithWorkerShouldReturnCorrectListIfServiceTakesSeveralTimeslots() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);
        when(orderDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(ORDER_ENTITIES.get(invocation.getArgument(0, Integer.class))));

        when(timeslotMapper.mapEntityToDomain(any())).thenAnswer(
                invocation -> Timeslot.builder()
                        .setId(invocation.getArgument(0, TimeslotEntity.class).getId())
                        .build());

        Service service = Service.builder()
                .setId(3)
                .setDuration(Duration.ofMinutes(50))
                .build();
        User worker = User.builder()
                .setId(3)
                .build();

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findTimeslotsForServiceWithWorker(
                122, service, worker);

        List<Integer> timeslotIds = consecutiveFreeTimeslots.stream()
                .map(Timeslot::getId)
                .collect(Collectors.toList());

        assertThat(timeslotIds, equalTo(Arrays.asList(122, 123)));
    }

    @Test
    public void findFreeTimeslotsForServiceWithWorkerShouldReturnEmptyListIfThereIsPauseBetweenTimeslots() {
        when(timeslotDao.findAllTimeslotsOfTheSameDaySorted(anyInt())).thenReturn(TIMESLOT_ENTITIES_BY_DAY);
        when(orderDao.findById(anyInt())).thenAnswer(
                invocation -> Optional.of(ORDER_ENTITIES.get(invocation.getArgument(0, Integer.class))));

        when(timeslotMapper.mapEntityToDomain(any())).thenAnswer(
                invocation -> Timeslot.builder()
                        .setId(invocation.getArgument(0, TimeslotEntity.class).getId())
                        .build());

        Service service = Service.builder()
                .setId(1)
                .setDuration(Duration.ofMinutes(60))
                .build();
        User user = User.builder()
                .setId(1)
                .build();

        List<Timeslot> consecutiveFreeTimeslots = timeslotService.findTimeslotsForServiceWithWorker(
                125, service, user);

        assertThat(consecutiveFreeTimeslots, equalTo(Collections.emptyList()));
    }

    private static List<OrderEntity> initOrderEntities() {
        return Arrays.asList(
                OrderEntity.builder()
                        .setId(0)
                        .setClient(UserEntity.builder()
                                .setId(1)
                                .build())
                        .setService(ServiceEntity.builder()
                                .setId(1)
                                .build())
                        .setWorker(UserEntity.builder()
                                .setId(1)
                                .build())
                        .build(),
                OrderEntity.builder()
                        .setId(1)
                        .setService(ServiceEntity.builder()
                                .setId(1)
                                .build())
                        .setWorker(UserEntity.builder()
                                .setId(2)
                                .build())
                        .build(),
                OrderEntity.builder()
                        .setId(2)
                        .setService(ServiceEntity.builder()
                                .setId(2)
                                .build())
                        .setWorker(UserEntity.builder()
                                .setId(1)
                                .build())
                        .build(),
                OrderEntity.builder()
                        .setId(3)
                        .setService(ServiceEntity.builder()
                                .setId(3)
                                .build())
                        .setWorker(UserEntity.builder()
                                .setId(2)
                                .build())
                        .build()
        );
    }

    private static List<Order> initOrders() {
        return Arrays.asList(
                Order.builder()
                        .setService(Service.builder()
                                .setId(1)
                                .setDuration(Duration.ofMinutes(30))
                                .build())
                        .setWorker(User.builder()
                                .setId(1)
                                .build())
                        .build(),
                Order.builder()
                        .setService(Service.builder()
                                .setId(1)
                                .setDuration(Duration.ofMinutes(30))
                                .build())
                        .setWorker(User.builder()
                                .setId(2)
                                .build())
                        .build(),
                Order.builder()
                        .setService(Service.builder()
                                .setId(1)
                                .setDuration(Duration.ofMinutes(30))
                                .build())
                        .setWorker(User.builder()
                                .setId(1)
                                .build())
                        .build(),
                Order.builder()
                        .setService(Service.builder()
                                .setId(3)
                                .setDuration(Duration.ofMinutes(60))
                                .build())
                        .setWorker(User.builder()
                                .setId(2)
                                .build())
                        .build()
        );
    }

    private static List<TimeslotEntity> initTimeslotEntitiesByDay() {
        DurationEntity duration = new DurationEntity(1, 30);

        return Arrays.asList(
                TimeslotEntity.builder()
                        .setId(122)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(8, 0))
                        .setDuration(duration)
                        .setOrders(Collections.singletonList(ORDER_ENTITIES.get(0)))
                        .build(),
                TimeslotEntity.builder()
                        .setId(123)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(8, 30))
                        .setDuration(duration)
                        .setOrders(Arrays.asList(
                                ORDER_ENTITIES.get(1), ORDER_ENTITIES.get(2)
                        ))
                        .build(),
                TimeslotEntity.builder()
                        .setId(124)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(9, 0))
                        .setDuration(duration)
                        .setOrders(Collections.singletonList(
                                ORDER_ENTITIES.get(3)
                        ))
                        .build(),
                TimeslotEntity.builder()
                        .setId(125)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(9, 30))
                        .setDuration(duration)
                        .setOrders(Collections.singletonList(
                                ORDER_ENTITIES.get(3)
                        ))
                        .build(),
                TimeslotEntity.builder()
                        .setId(126)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(10, 30))
                        .setDuration(duration)
                        .build(),
                TimeslotEntity.builder()
                        .setId(126)
                        .setDate(LocalDate.of(2020, 2, 2))
                        .setFromTime(LocalTime.of(11, 0))
                        .setDuration(duration)
                        .build()
        );
    }

    private static List<TimeslotEntity> initTimeslotEntities() {
        return Arrays.asList(
                TimeslotEntity.builder()
                        .setId(122)
                        .setDate(LocalDate.of(2020, 2, 5))
                        .setOrders(Collections.singletonList(ORDER_ENTITIES.get(0)))
                        .build(),
                TimeslotEntity.builder()
                        .setId(123)
                        .setDate(LocalDate.of(2020, 2, 5))
                        .build(),
                TimeslotEntity.builder()
                        .setId(124)
                        .setDate(LocalDate.of(2020, 2, 7))
                        .setOrders(Arrays.asList(ORDER_ENTITIES.get(1), ORDER_ENTITIES.get(2)))
                        .build()
        );
    }

    private static List<Timetable> initTimetables() {
        Order order = Order.builder().build();

        return Arrays.asList(
                new Timetable(LocalDate.of(2020, 2, 4), Collections.emptyList()),
                new Timetable(LocalDate.of(2020, 2, 5),
                        Arrays.asList(
                                Timeslot.builder()
                                        .setId(122)
                                        .setAvailable(true)
                                        .setDate(LocalDate.of(2020, 2, 5))
                                        .setOrders(Collections.singletonList(order))
                                        .build(),
                                Timeslot.builder()
                                        .setId(123)
                                        .setAvailable(true)
                                        .setDate(LocalDate.of(2020, 2, 5))
                                        .build())
                ),
                new Timetable(LocalDate.of(2020, 2, 6), Collections.emptyList()),
                new Timetable(LocalDate.of(2020, 2, 7),
                        Collections.singletonList(Timeslot.builder()
                                .setId(124)
                                .setAvailable(false)
                                .setDate(LocalDate.of(2020, 2, 7))
                                .setOrders(Arrays.asList(order, order))
                                .build())
                ),
                new Timetable(LocalDate.of(2020, 2, 8), Collections.emptyList()));
    }
}