package com.salon.booking.mapper;

import com.salon.booking.domain.Notification;
import com.salon.booking.domain.NotificationType;
import com.salon.booking.domain.User;
import com.salon.booking.entity.NotificationEntity;
import com.salon.booking.entity.NotificationTypeEntity;
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
    private Mapper<UserEntity, User> userMapper;

    @Mock
    private Mapper<NotificationTypeEntity, NotificationType> notificationTypeMapper;

    @InjectMocks
    private NotificationMapper notificationMapper;

    @Before
    public void injectMocks() {
        notificationMapper = new NotificationMapper(userMapper, notificationTypeMapper);
    }

    @After
    public void resetMocks() {
        reset(userMapper, notificationTypeMapper);
    }

    @Test
    public void mapDomainToEntityShouldMapCorrectly() {
        User user = User.builder()
                .setId(1)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .setId(1)
                .build();

        when(userMapper.mapDomainToEntity(eq(user))).thenReturn(userEntity);
        when(notificationTypeMapper.mapDomainToEntity(eq(NotificationType.LEAVE_FEEDBACK)))
                .thenReturn(NotificationTypeEntity.LEAVE_FEEDBACK);

        Notification notification = Notification.builder()
                .setId(1)
                .setText("text")
                .setType(NotificationType.LEAVE_FEEDBACK)
                .setUser(user)
                .build();

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .setId(1)
                .setText("text")
                .setType(NotificationTypeEntity.LEAVE_FEEDBACK)
                .setRead(false)
                .setUser(userEntity)
                .build();

        assertEquals(notificationEntity, notificationMapper.mapDomainToEntity(notification));
    }

    @Test
    public void mapEntityToDomainShouldMapCorrectly() {
        User user = User.builder()
                .setId(1)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .setId(1)
                .build();

        when(userMapper.mapEntityToDomain(eq(userEntity))).thenReturn(user);
        when(notificationTypeMapper.mapEntityToDomain(eq(NotificationTypeEntity.LEAVE_FEEDBACK)))
                .thenReturn(NotificationType.LEAVE_FEEDBACK);

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .setId(1)
                .setText("text")
                .setType(NotificationTypeEntity.LEAVE_FEEDBACK)
                .setUser(userEntity)
                .build();

        Notification notification = Notification.builder()
                .setId(1)
                .setText("text")
                .setType(NotificationType.LEAVE_FEEDBACK)
                .setUser(user)
                .build();

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