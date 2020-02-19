package com.salon.booking.service.impl;

import com.salon.booking.dao.TimeslotDao;
import com.salon.booking.dao.TransactionManager;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.Service;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.dao.OrderDao;
import com.salon.booking.dao.ServiceDao;
import com.salon.booking.dao.UserDao;
import com.salon.booking.domain.User;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.RoleEntity;
import com.salon.booking.entity.ServiceEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.service.TimeslotService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private ServiceDao serviceDao;

    @Mock
    private UserDao userDao;

    @Mock
    private TimeslotService timeslotService;

    @Mock
    private Mapper<OrderEntity, Order> orderMapper;

    @Mock
    private Mapper<ServiceEntity, Service> serviceMapper;

    @Mock
    private TransactionManager transactionManager;

    private OrderServiceImpl orderService;

    @Before
    public void injectMocks() {
        orderService = new OrderServiceImpl(timeslotService, orderDao, serviceDao, userDao,
                orderMapper, serviceMapper,
                transactionManager);
    }

    @After
    public void resetMocks() {
        reset(orderDao, serviceDao, userDao, orderMapper, serviceMapper);
    }

    @Test
    public void findAllByClientIdShouldReturnMappedUsers() {
        User client = User.builder()
                .setId(1)
                .setRole(Role.CLIENT)
                .build();
        UserEntity clientEntity = UserEntity.builder()
                .setId(1)
                .setRole(RoleEntity.CLIENT)
                .build();

        User worker = User.builder().setId(2).setRole(Role.WORKER).build();
        UserEntity workerEntity = UserEntity.builder()
                .setId(2)
                .setRole(RoleEntity.WORKER)
                .build();

        Service service = Service.builder()
                .setId(3)
                .build();
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .setId(3)
                .build();

        Order order = Order.builder()
                .setService(service)
                .setWorker(worker)
                .setClient(client)
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .setService(serviceEntity)
                .setWorker(workerEntity)
                .setClient(clientEntity)
                .build();

        when(orderDao.findAllByClientId(anyInt())).thenReturn(Collections.singletonList(orderEntity));
        when(orderMapper.mapEntityToDomain(eq(orderEntity))).thenReturn(order);
        when(serviceDao.findById(anyInt())).thenReturn(Optional.of(serviceEntity));
        when(userDao.findById(anyInt())).thenReturn(Optional.of(workerEntity));

        List<Order> orders = orderService.findAllByClientId(123);

        assertEquals(Collections.singletonList(order), orders);
    }
}