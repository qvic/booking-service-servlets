package com.salon.booking.service.impl;

import com.salon.booking.dao.NotificationDao;
import com.salon.booking.domain.Notification;
import com.salon.booking.domain.Order;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    @Mock
    private OrderService orderService;

    @Mock
    private NotificationDao notificationDao;

    @Mock
    private Mapper<NotificationEntity, Notification> notificationMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    public void findAllUnreadAndMarkAllAsRead() {
        Order order = Order.builder().build();
        OrderEntity orderEntity = OrderEntity.builder().build();
        when(orderService.findById(any())).thenReturn(Optional.of(order));

        Notification notification = new Notification(1, order, false);
        NotificationEntity notificationEntity = new NotificationEntity(1, orderEntity, false);
        when(notificationDao.findAllUnread(eq(2))).thenReturn(Collections.singletonList(notificationEntity));
        when(notificationMapper.mapEntityToDomain(eq(notificationEntity))).thenReturn(notification);

        List<Notification> unread = notificationService.findAllUnreadAndMarkAllAsRead(2);

        assertThat(unread, equalTo(Collections.singletonList(notification)));
        verify(notificationDao).updateAllAsRead(eq(2));
    }

    @Test
    public void findAllRead() {
        Order order = Order.builder().build();
        OrderEntity orderEntity = OrderEntity.builder().build();
        when(orderService.findById(any())).thenReturn(Optional.of(order));

        Notification notification = new Notification(1, order, true);
        NotificationEntity notificationEntity = new NotificationEntity(1, orderEntity, true);
        when(notificationDao.findAllRead(eq(2))).thenReturn(Collections.singletonList(notificationEntity));
        when(notificationMapper.mapEntityToDomain(eq(notificationEntity))).thenReturn(notification);

        List<Notification> read = notificationService.findAllRead(2);

        assertThat(read, equalTo(Collections.singletonList(notification)));
    }

    @Test
    public void updateNotificationsReturningCount() {
        List<Order> orders = Arrays.asList(
                Order.builder().setId(0).build(),
                Order.builder().setId(1).build(),
                Order.builder().setId(2).build()
        );
        List<OrderEntity> orderEntities = Arrays.asList(
                OrderEntity.builder().setId(0).build(),
                OrderEntity.builder().setId(1).build(),
                OrderEntity.builder().setId(2).build()
        );

        when(notificationDao.findByOrderId(eq(0)))
                .thenReturn(Optional.of(new NotificationEntity(10, orderEntities.get(0), true)));
        when(notificationDao.findByOrderId(eq(1)))
                .thenReturn(Optional.of(new NotificationEntity(11, orderEntities.get(1), false)));
        when(notificationDao.findByOrderId(eq(2)))
                .thenReturn(Optional.empty());

        when(orderService.findFinishedOrdersAfter(any(), any())).thenReturn(orders);

        int unreadCount = notificationService.updateNotificationsReturningCount(2, null);

        assertThat(unreadCount, equalTo(2));
        verify(notificationDao).save(eq(new NotificationEntity(null, orderEntities.get(2), false)));
    }
}