package com.bookingservice.service.impl;

import com.bookingservice.dao.OrderDao;
import com.bookingservice.dao.ServiceDao;
import com.bookingservice.dao.TimeslotDao;
import com.bookingservice.dao.TransactionManager;
import com.bookingservice.dao.UserDao;
import com.bookingservice.dao.exception.DatabaseRuntimeException;
import com.bookingservice.domain.Order;
import com.bookingservice.domain.Service;
import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.ServiceEntity;
import com.bookingservice.entity.TimeslotEntity;
import com.bookingservice.entity.UserEntity;
import com.bookingservice.mapper.Mapper;
import com.bookingservice.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(TimeslotServiceImpl.class);

    private final OrderDao orderDao;
    private final ServiceDao serviceDao;
    private final UserDao userDao;
    private final TimeslotDao timeslotDao;

    private final Mapper<OrderEntity, Order> orderMapper;
    private final Mapper<ServiceEntity, Service> serviceMapper;
    private TransactionManager transactionManager;

    public OrderServiceImpl(OrderDao orderDao, ServiceDao serviceDao, UserDao userDao,
                            TimeslotDao timeslotDao, Mapper<OrderEntity, Order> orderMapper, Mapper<ServiceEntity, Service> serviceMapper,
                            TransactionManager transactionManager) {
        this.orderDao = orderDao;
        this.serviceDao = serviceDao;
        this.userDao = userDao;
        this.timeslotDao = timeslotDao;
        this.orderMapper = orderMapper;
        this.serviceMapper = serviceMapper;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Order> findAllByClientId(Integer clientId) {
        return orderDao.findAllByClientId(clientId)
                .stream()
                .map(this::buildWithServiceAndWorker)
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    private OrderEntity buildWithServiceAndWorker(OrderEntity orderEntity) {
        ServiceEntity serviceById = serviceDao.findById(orderEntity.getService().getId())
                .orElseThrow(() -> new RuntimeException("Service id mapped to Order is not present in data source"));
        UserEntity workerById = userDao.findById(orderEntity.getWorker().getId())
                .orElseThrow(() -> new RuntimeException("Worker id mapped to Order is not present in data source"));

        return OrderEntity.builder(orderEntity)
                .setService(serviceById)
                .setWorker(workerById)
                .build();
    }

    @Override
    public List<Order> findAllByWorkerId(Integer workerId) {
        return orderDao.findAllByWorkerId(workerId)
                .stream()
                .map(orderMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findAllServices() {
        return serviceDao.findAll()
                .stream()
                .map(serviceMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void saveOrderUpdatingTimeslots(Integer selectedTimeslotId, Order order) {
        OrderEntity orderEntity = orderMapper.mapDomainToEntity(order);

        try {
            transactionManager.beginTransaction();

            OrderEntity savedOrder = orderDao.save(orderEntity);
            timeslotDao.updateOrderId(selectedTimeslotId, savedOrder.getId());

            transactionManager.commitTransaction();
        } catch (DatabaseRuntimeException e) {
            LOGGER.error(e);
            transactionManager.rollbackTransaction();
        }
    }
}
