package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.domain.Timetable;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.TimeslotEntity;
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

    public TimeslotServiceImpl(TimeslotDao timeslotDao, OrderDao orderDao) {
        this.timeslotDao = timeslotDao;
        this.orderDao = orderDao;
    }

    @Override
    public List<Timetable> findAllBetween(LocalDate from, LocalDate to) {
        List<TimeslotEntity> timeslots = timeslotDao.findAllBetween(from, to);

        List<Timetable> timetables = new ArrayList<>();

        Map<LocalDate, List<TimeslotEntity>> groupedTimeslots = timeslots
                .stream()
                .collect(Collectors.groupingBy(TimeslotEntity::getDate));

        for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1)) {
            List<TimeslotEntity> timeslotEntities = groupedTimeslots.getOrDefault(date, Collections.emptyList());

            List<Timeslot> rows = timeslotEntities
                    .stream()
                    .map(this::buildTimetableRow)
                    .collect(Collectors.toList());

            timetables.add(new Timetable(date, rows));
        }

        return timetables;
    }

    private Timeslot buildTimetableRow(TimeslotEntity timeslotEntity) {
        Optional<OrderEntity> order = Optional.ofNullable(timeslotEntity.getOrder())
                .flatMap(orderEntity -> orderDao.findById(orderEntity.getId()));

        return new Timeslot(timeslotEntity.getFromTime(),
                timeslotEntity.getToTime(),
                order.orElse(null));
    }
}
