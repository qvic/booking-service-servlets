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
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private static final Period FEEDBACK_THRESHOLD = Period.ofDays(7);

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
                .map(this::buildWithServiceAndWorker)
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    private OrderEntity buildWithServiceAndWorker(OrderEntity orderEntity) {
        ServiceEntity serviceById = serviceDao.findById(orderEntity.getService().getId())
                .orElseThrow(() -> new RuntimeException("Service id mapped to OrderEntity is not present in data source"));
        UserEntity workerById = userDao.findById(orderEntity.getWorker().getId())
                .orElseThrow(() -> new RuntimeException("Worker id mapped to OrderEntity is not present in data source"));

        return OrderEntity.builder(orderEntity)
                .setService(serviceById)
                .setWorker(workerById)
                .build();
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
    public List<Order> findLastFinishedOrders() {
        return orderDao.findAllFinishedAfter(LocalDateTime.now().minus(FEEDBACK_THRESHOLD)).stream()
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

    private void assignOrderToTimeslots(List<Timeslot> timeslots, Integer orderId) {
        timeslots.forEach(timeslot -> timeslotService.saveOrderTimeslot(timeslot.getId(), orderId));
    }
}
