package com.bookingservice.service.impl;

import com.bookingservice.dao.OrderDao;
import com.bookingservice.dao.ServiceDao;
import com.bookingservice.dao.TimeslotDao;
import com.bookingservice.dao.UserDao;
import com.bookingservice.domain.Timeslot;
import com.bookingservice.domain.Timetable;
import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.ServiceEntity;
import com.bookingservice.entity.TimeslotEntity;
import com.bookingservice.entity.UserEntity;
import com.bookingservice.mapper.Mapper;
import com.bookingservice.service.TimeslotService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TimeslotServiceImpl implements TimeslotService {

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
        List<TimeslotEntity> timeslots = timeslotDao.findAllBetween(fromInclusive, toExclusive);
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
    public List<Timeslot> findConsecutiveFreeTimeslots(Integer startingTimeslotId) {
        return timeslotDao.findConsecutiveFreeTimeslots(startingTimeslotId).stream()
                .map(timeslotMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    private Timeslot buildTimeslot(TimeslotEntity timeslotEntity) {
        Optional<OrderEntity> order = Optional.ofNullable(timeslotEntity.getOrder())
                .flatMap(orderEntity -> orderDao.findById(orderEntity.getId()))
                .map(this::buildOrderEntity);

        TimeslotEntity entityWithOrder = TimeslotEntity.builder(timeslotEntity)
                .setOrder(order.orElse(null))
                .build();

        return timeslotMapper.mapEntityToDomain(entityWithOrder);
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
        return userDao.findById(orderEntity.getClient().getId()) // todo findByIdAndRole
                .orElseThrow(() -> new IllegalArgumentException("Can't find UserEntity with such id"));
    }

    private UserEntity getWorker(OrderEntity orderEntity) {
        return userDao.findById(orderEntity.getWorker().getId()) // todo findByIdAndRole
                .orElseThrow(() -> new IllegalArgumentException("Can't find UserEntity with such id"));
    }
}
