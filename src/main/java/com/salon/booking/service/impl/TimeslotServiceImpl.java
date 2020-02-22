package com.salon.booking.service.impl;

import com.salon.booking.dao.OrderDao;
import com.salon.booking.dao.ServiceDao;
import com.salon.booking.dao.TimeslotDao;
import com.salon.booking.dao.UserDao;
import com.salon.booking.domain.Service;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.Timetable;
import com.salon.booking.domain.User;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.ServiceEntity;
import com.salon.booking.entity.TimeslotEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.TimeslotService;
import com.salon.booking.utility.StreamUtility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static com.salon.booking.utility.ListUtility.getIndexOfFirstMatch;

public class TimeslotServiceImpl implements TimeslotService {

    private static final Period DEFAULT_TIMETABLE_PERIOD = Period.ofDays(14);

    private final TimeslotDao timeslotDao;
    private final OrderDao orderDao;
    private final ServiceDao serviceDao;
    private final UserDao userDao;
    private final Mapper<TimeslotEntity, Timeslot> timeslotMapper;

    public TimeslotServiceImpl(TimeslotDao timeslotDao, OrderDao orderDao, ServiceDao serviceDao, UserDao userDao,
                               Mapper<TimeslotEntity, Timeslot> timeslotMapper) {
        this.timeslotDao = timeslotDao;
        this.orderDao = orderDao;
        this.serviceDao = serviceDao;
        this.userDao = userDao;
        this.timeslotMapper = timeslotMapper;
    }

    @Override
    public List<Timetable> findAllBetween(LocalDate fromInclusive, LocalDate toExclusive) {
        List<TimeslotEntity> timeslots = timeslotDao.findAllBetweenDatesSorted(fromInclusive, toExclusive);
        List<Timetable> timetables = new ArrayList<>();

        Map<LocalDate, List<TimeslotEntity>> groupedTimeslots = timeslots.stream()
                .collect(Collectors.groupingBy(TimeslotEntity::getDate));

        LocalDate date = fromInclusive;

        while (date.isBefore(toExclusive)) {
            List<TimeslotEntity> timeslotEntities = groupedTimeslots.getOrDefault(date, Collections.emptyList());

            List<Timeslot> rows = timeslotEntities
                    .stream()
                    .map(this::buildTimeslot)
                    .collect(Collectors.toList());

            timetables.add(new Timetable(date, rows));

            date = date.plusDays(1);
        }

        return timetables;
    }

    @Override
    public List<Timetable> findTimetablesForServiceWithWorker(Integer serviceId, Integer workerId) {
        LocalDate from = LocalDate.now();
        LocalDate to = from.plus(DEFAULT_TIMETABLE_PERIOD);

        return findAllBetween(from, to);
    }

    private Timeslot buildTimeslot(TimeslotEntity timeslotEntity) {
        long servicesCount = serviceDao.count();

        List<OrderEntity> orders = timeslotEntity.getOrders().stream()
                .map(orderEntity -> orderDao.findById(orderEntity.getId()))
                .flatMap(StreamUtility::toStream)
                .map(this::buildOrderEntity)
                .collect(Collectors.toList());

        Timeslot timeslot = timeslotMapper.mapEntityToDomain(
                TimeslotEntity.builder(timeslotEntity)
                        .setOrders(orders)
                        .build());

        return Timeslot.builder(timeslot)
                .setAvailable(orders.size() < servicesCount)
                .build();
    }

    private OrderEntity buildOrderEntity(OrderEntity orderEntity) {
        return OrderEntity.builder(orderEntity)
                .setWorker(getWorker(orderEntity))
                .setClient(getClient(orderEntity))
                .setService(getService(orderEntity))
                .build();
    }

    private ServiceEntity getService(OrderEntity orderEntity) {
        return serviceDao.findById(orderEntity.getService().getId())
                .orElseThrow(() -> new IllegalArgumentException("Can't find ServiceEntity with such id"));
    }

    private UserEntity getClient(OrderEntity orderEntity) {
        return userDao.findById(orderEntity.getClient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Can't find UserEntity with such id"));
    }

    private UserEntity getWorker(OrderEntity orderEntity) {
        return userDao.findById(orderEntity.getWorker().getId())
                .orElseThrow(() -> new IllegalArgumentException("Can't find UserEntity with such id"));
    }

    @Override
    public List<Timeslot> findTimeslotsForServiceWithWorker(Integer startingTimeslotId,
                                                            Service service, User worker) {

        List<TimeslotEntity> timeslots = timeslotDao.findAllTimeslotsOfTheSameDaySorted(startingTimeslotId);
        ServiceEntity serviceEntity = serviceDao.findById(service.getId())
                .orElseThrow(() -> new IllegalArgumentException("Can't find service with such id"));
        int serviceDuration = serviceEntity.getDurationMinutes();

        List<Timeslot> freeTimeslots = new ArrayList<>();
        int currentFreeDuration = 0;

        OptionalInt startingTimeslotIndex = getIndexOfFirstMatch(timeslots, t -> t.getId().equals(startingTimeslotId));

        if (!startingTimeslotIndex.isPresent()) {
            return freeTimeslots;
        }

        for (int i = startingTimeslotIndex.getAsInt(); i < timeslots.size(); i++) {
            TimeslotEntity timeslot = timeslots.get(i);

            boolean isValidTimeslot = isConsecutiveTimeslot(timeslots, i) &&
                    isValidTimeslot(timeslot, service.getId(), worker.getId());

            if (currentFreeDuration < serviceEntity.getDurationMinutes() && isValidTimeslot) {
                freeTimeslots.add(timeslotMapper.mapEntityToDomain(timeslot));
                currentFreeDuration += timeslot.getDuration().getMinutes();
            } else {
                break;
            }
        }

        if (currentFreeDuration < serviceDuration) {
            return Collections.emptyList();
        }

        return freeTimeslots;
    }

    private boolean isConsecutiveTimeslot(List<TimeslotEntity> timeslots, int i) {
        if (i < 1) {
            return true;
        }

        TimeslotEntity previousTimeslot = timeslots.get(i - 1);
        LocalTime previousTimeslotEndTime = previousTimeslot.getFromTime().plusMinutes(previousTimeslot.getDuration().getMinutes());
        LocalTime currentTimeslotStartTime = timeslots.get(i).getFromTime();

        return previousTimeslotEndTime.equals(currentTimeslotStartTime);
    }

    private boolean isValidTimeslot(TimeslotEntity timeslot, Integer serviceId, Integer workerId) {
        return timeslot.getOrders().stream()
                .map(o -> orderDao.findById(o.getId()))
                .flatMap(StreamUtility::toStream)
                .allMatch(o -> !o.getService().getId().equals(serviceId) && !o.getWorker().getId().equals(workerId));
    }

    @Override
    public void saveOrderTimeslot(Integer timeslotId, Integer orderId) {
        timeslotDao.saveOrderTimeslot(timeslotId, orderId);
    }
}
