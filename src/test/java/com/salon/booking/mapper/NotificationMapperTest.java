package com.salon.booking.mapper;

import com.salon.booking.domain.Notification;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.User;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.entity.UserEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationMapperTest {

    @Mock
    private Mapper<OrderEntity, Order> orderMapper;

    @InjectMocks
    private NotificationMapper notificationMapper;

    @Before
    public void injectMocks() {
        notificationMapper = new NotificationMapper(orderMapper);
    }

    @After
    public void resetMocks() {
        reset(orderMapper);
    }

    @Test
    public void mapDomainToEntityShouldMapCorrectly() {
        Order order = Order.builder()
                .setId(1)
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .setId(1)
                .build();

        when(orderMapper.mapDomainToEntity(eq(order))).thenReturn(orderEntity);

        Notification notification = new Notification(1, order, false);
        NotificationEntity notificationEntity = new NotificationEntity(1, orderEntity, false);

        assertEquals(notificationEntity, notificationMapper.mapDomainToEntity(notification));
    }

    @Test
    public void mapEntityToDomainShouldMapCorrectly() {
        Order order = Order.builder()
                .setId(1)
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .setId(1)
                .build();

        when(orderMapper.mapEntityToDomain(eq(orderEntity))).thenReturn(order);

        NotificationEntity notificationEntity = new NotificationEntity(1, orderEntity, false);
        Notification notification = new Notification(1, order, false);

        assertEquals(notification, notificationMapper.mapEntityToDomain(notificationEntity));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(notificationMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        assertNull(notificationMapper.mapEntityToDomain(null));
    }
}