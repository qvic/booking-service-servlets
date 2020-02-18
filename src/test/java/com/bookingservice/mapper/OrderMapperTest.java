package com.bookingservice.mapper;

import com.bookingservice.domain.Order;
import com.bookingservice.domain.Service;
import com.bookingservice.domain.User;
import com.bookingservice.entity.OrderEntity;
import com.bookingservice.entity.ServiceEntity;
import com.bookingservice.entity.UserEntity;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderMapperTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private ServiceMapper serviceMapper;

    private OrderMapper orderMapper;

    @After
    public void resetMocks() {
        reset(userMapper, serviceMapper);
    }

    @Before
    public void injectMocks() {
        orderMapper = new OrderMapper(userMapper, serviceMapper);
    }

    @Test
    public void mapDomainToEntityShouldMapCorrectly() {
        User client = User.builder()
                .setId(1)
                .build();

        UserEntity clientEntity = UserEntity.builder()
                .setId(1)
                .build();

        User worker = User.builder()
                .setId(2)
                .build();

        UserEntity workerEntity = UserEntity.builder()
                .setId(2)
                .build();

        Service service = Service.builder()
                .setId(1)
                .build();

        ServiceEntity serviceEntity = ServiceEntity.builder()
                .setId(1)
                .build();

        LocalDateTime dateTime = LocalDateTime.of(2020, 2, 2, 2, 2);

        when(userMapper.mapDomainToEntity(eq(client))).thenReturn(clientEntity);
        when(userMapper.mapDomainToEntity(eq(worker))).thenReturn(workerEntity);
        when(serviceMapper.mapDomainToEntity(eq(service))).thenReturn(serviceEntity);

        Order order = Order.builder()
                .setClient(client)
                .setWorker(worker)
                .setDate(dateTime)
                .setService(service)
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .setClient(clientEntity)
                .setWorker(workerEntity)
                .setDate(dateTime)
                .setService(serviceEntity)
                .build();

        assertEquals(orderEntity, orderMapper.mapDomainToEntity(order));
    }

    @Test
    public void mapEntityToDomainShouldMapCorrectly() {
        User client = User.builder()
                .setId(1)
                .build();

        UserEntity clientEntity = UserEntity.builder()
                .setId(1)
                .build();

        User worker = User.builder()
                .setId(2)
                .build();

        UserEntity workerEntity = UserEntity.builder()
                .setId(2)
                .build();

        Service service = Service.builder()
                .setId(1)
                .build();

        ServiceEntity serviceEntity = ServiceEntity.builder()
                .setId(1)
                .build();

        LocalDateTime dateTime = LocalDateTime.of(2020, 2, 2, 2, 2);

        when(userMapper.mapEntityToDomain(eq(clientEntity))).thenReturn(client);
        when(userMapper.mapEntityToDomain(eq(workerEntity))).thenReturn(worker);
        when(serviceMapper.mapEntityToDomain(eq(serviceEntity))).thenReturn(service);

        OrderEntity orderEntity = OrderEntity.builder()
                .setClient(clientEntity)
                .setWorker(workerEntity)
                .setDate(dateTime)
                .setService(serviceEntity)
                .setId(1)
                .build();

        Order order = Order.builder()
                .setClient(client)
                .setWorker(worker)
                .setDate(dateTime)
                .setService(service)
                .build();

        TestCase.assertEquals(order, orderMapper.mapEntityToDomain(orderEntity));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(orderMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        TestCase.assertNull(orderMapper.mapEntityToDomain(null));
    }
}