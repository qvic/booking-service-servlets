package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.OrderDao;
import com.epam.bookingservice.dao.ServiceDao;
import com.epam.bookingservice.dao.TimeslotDao;
import com.epam.bookingservice.dao.UserDao;
import com.epam.bookingservice.dao.exception.DatabaseRuntimeException;
import com.epam.bookingservice.dao.TransactionManager;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.domain.Timetable;
import com.epam.bookingservice.entity.OrderEntity;
import com.epam.bookingservice.entity.ServiceEntity;
import com.epam.bookingservice.entity.TimeslotEntity;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.mapper.Mapper;
import com.epam.bookingservice.service.TimeslotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TimeslotServiceImpl implements TimeslotService {

    private static final Logger LOGGER = LogManager.getLogger(TimeslotServiceImpl.class);

    private final TimeslotDao timeslotDao;
    private final OrderDao orderDao;
    private final ServiceDao serviceDao;
    private final UserDao userDao;

    private final Mapper<TimeslotEntity, Timeslot> timeslotMapper;

    private TransactionManager transactionManager;

    public TimeslotServiceImpl(TimeslotDao timeslotDao, OrderDao orderDao,
                               ServiceDao serviceDao, UserDao userDao, Mapper<TimeslotEntity, Timeslot> timeslotMapper,
                               TransactionManager transactionManager) {
        this.timeslotDao = timeslotDao;
        this.orderDao = orderDao;
        this.serviceDao = serviceDao;
        this.userDao = userDao;
        this.timeslotMapper = timeslotMapper;
        this.transactionManager = transactionManager;
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
                .orElse(null);
    }

    private UserEntity getClient(OrderEntity orderEntity) {
        return userDao.findById(orderEntity.getClient().getId())
                .orElse(null);
    }

    private UserEntity getWorker(OrderEntity orderEntity) {
        return userDao.findById(orderEntity.getWorker().getId())
                .orElse(null);
    }

    @Override
    public void updateTimeslotWithOrder(Timeslot timeslot) {
        TimeslotEntity timeslotEntity = timeslotMapper.mapDomainToEntity(timeslot);

        try {
            transactionManager.beginTransaction();

            OrderEntity savedOrder = orderDao.save(timeslotEntity.getOrder());
            TimeslotEntity updatedTimeslot = TimeslotEntity.builder(timeslotEntity)
                    .setOrder(savedOrder)
                    .build();
            timeslotDao.updateOrder(updatedTimeslot);

            transactionManager.commitTransaction();
        } catch (DatabaseRuntimeException e) {
            LOGGER.error(e);
            transactionManager.rollbackTransaction();
        }
    }
}
