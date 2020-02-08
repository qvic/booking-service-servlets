package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.domain.Timetable;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.service.TimeslotService;

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

    private final Mapper<TimeslotEntity, Timeslot> timeslotMapper;

    public TimeslotServiceImpl(TimeslotDao timeslotDao, OrderDao orderDao,
                               Mapper<TimeslotEntity, Timeslot> timeslotMapper) {
        this.timeslotDao = timeslotDao;
        this.orderDao = orderDao;
        this.timeslotMapper = timeslotMapper;
    }

    @Override
    public List<Timetable> findAllBetween(LocalDate fromInclusive, LocalDate toExclusive) {
        List<TimeslotEntity> timeslots = timeslotDao.findAllBetween(fromInclusive, toExclusive);
        List<Timetable> timetables = new ArrayList<>();

        Map<LocalDate, List<TimeslotEntity>> groupedTimeslots = timeslots.stream()
                .collect(Collectors.groupingBy(TimeslotEntity::getDate));

        for (LocalDate date = fromInclusive; date.isBefore(toExclusive); date = date.plusDays(1)) {

            List<TimeslotEntity> timeslotEntities = groupedTimeslots.getOrDefault(date, Collections.emptyList());

            List<Timeslot> rows = timeslotEntities
                    .stream()
                    .map(this::buildTimeslot)
                    .collect(Collectors.toList());

            timetables.add(new Timetable(date, rows));
        }

        return timetables;
    }

    @Override
    public void updateTimeslotWithOrder(Timeslot timeslot) {
        TimeslotEntity timeslotEntity = timeslotMapper.mapDomainToEntity(timeslot);

        timeslotDao.saveOrderAndUpdateTimeslot(timeslotEntity);
    }

    private Timeslot buildTimeslot(TimeslotEntity timeslotEntity) {
        Optional<OrderEntity> order = Optional.ofNullable(timeslotEntity.getOrder())
                .flatMap(orderEntity -> orderDao.findById(orderEntity.getId()));

        TimeslotEntity entityWithOrder = TimeslotEntity.builder(timeslotEntity)
                .setOrder(order.orElse(null))
                .build();

        return timeslotMapper.mapEntityToDomain(entityWithOrder);
    }


}
