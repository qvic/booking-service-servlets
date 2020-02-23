package com.salon.booking.service.impl;

import com.salon.booking.dao.OrderDao;
import com.salon.booking.dao.ServiceDao;
import com.salon.booking.dao.TransactionManager;
import com.salon.booking.dao.UserDao;
import com.salon.booking.dao.exception.DatabaseRuntimeException;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Service;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.ServiceEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.TimeslotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private final TimeslotService timeslotService;

    private final OrderDao orderDao;
    private final ServiceDao serviceDao;
    private final UserDao userDao;

    private final Mapper<OrderEntity, Order> orderMapper;
    private final Mapper<ServiceEntity, Service> serviceMapper;

    private TransactionManager transactionManager;

    public OrderServiceImpl(TimeslotService timeslotService, OrderDao orderDao, ServiceDao serviceDao, UserDao userDao,
                            Mapper<OrderEntity, Order> orderMapper, Mapper<ServiceEntity, Service> serviceMapper,
                            TransactionManager transactionManager) {
        this.timeslotService = timeslotService;
        this.orderDao = orderDao;
        this.serviceDao = serviceDao;
        this.userDao = userDao;
        this.orderMapper = orderMapper;
        this.serviceMapper = serviceMapper;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Order> findAllByClientId(Integer clientId) {
        return orderDao.findAllByClientId(clientId).stream()
                .map(this::fillOrderEntity)
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAllByWorkerId(Integer workerId) {
        return orderDao.findAllByWorkerId(workerId).stream()
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void saveOrderUpdatingTimeslots(Integer selectedTimeslotId, Order order) {
        OrderEntity orderEntity = orderMapper.mapDomainToEntity(order);

        try {
            transactionManager.beginTransaction();

            OrderEntity savedOrder = orderDao.save(orderEntity);

            List<Timeslot> freeTimeslots = timeslotService.findTimeslotsForOrderWith(
                    selectedTimeslotId, order.getService(), order.getWorker());

            assignOrderToTimeslots(freeTimeslots, savedOrder.getId());

            transactionManager.commitTransaction();
        } catch (DatabaseRuntimeException e) {
            LOGGER.error(e);
            transactionManager.rollbackTransaction();
        }
    }

    @Override
    public List<Order> findFinishedOrdersAfter(LocalDateTime dateTime, Integer clientId) {
        return orderDao.findAllFinishedAfter(dateTime, clientId).stream()
                .map(this::fillOrderEntity)
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findAllServices() {
        return serviceDao.findAll().stream()
                .map(serviceMapper::mapEntityToDomain)
                .map(service -> Service.builder(service).setAvailable(true).build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Service> findServiceById(Integer id) {
        return serviceDao.findById(id)
                .map(serviceMapper::mapEntityToDomain);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderDao.findById(id)
                .map(this::fillOrderEntity)
                .map(orderMapper::mapEntityToDomain);
    }

    private OrderEntity fillOrderEntity(OrderEntity orderEntity) {
        ServiceEntity service = serviceDao.findById(orderEntity.getService().getId())
                .orElseThrow(NoSuchElementException::new);
        UserEntity worker = userDao.findById(orderEntity.getWorker().getId())
                .orElseThrow(NoSuchElementException::new);
        UserEntity client = userDao.findById(orderEntity.getClient().getId())
                .orElseThrow(NoSuchElementException::new);

        return OrderEntity.builder(orderEntity)
                .setService(service)
                .setWorker(worker)
                .setClient(client)
                .build();
    }


    private void assignOrderToTimeslots(List<Timeslot> timeslots, Integer orderId) {
        timeslots.forEach(timeslot -> timeslotService.saveOrderTimeslot(timeslot.getId(), orderId));
    }
}
