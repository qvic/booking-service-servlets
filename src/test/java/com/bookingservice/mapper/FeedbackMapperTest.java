package com.bookingservice.mapper;

import com.bookingservice.domain.Feedback;
import com.bookingservice.domain.User;
import com.bookingservice.entity.FeedbackEntity;
import com.bookingservice.entity.FeedbackStatusEntity;
import com.bookingservice.entity.UserEntity;
import junit.framework.TestCase;
import org.junit.After;
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
public class FeedbackMapperTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private FeedbackMapper feedbackMapper;

    @After
    public void resetMocks() {
        reset(userMapper);
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

        Feedback feedback = new Feedback("text", user);

        FeedbackEntity expectedFeedbackEntity = FeedbackEntity.builder()
                .setStatus(FeedbackStatusEntity.CREATED)
                .setText("text")
                .setWorker(userEntity)
                .build();

        assertEquals(expectedFeedbackEntity, feedbackMapper.mapDomainToEntity(feedback));
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

        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
                .setStatus(FeedbackStatusEntity.CREATED)
                .setText("text")
                .setWorker(userEntity)
                .build();

        Feedback expectedFeedback = new Feedback("text", user);

        TestCase.assertEquals(expectedFeedback, feedbackMapper.mapEntityToDomain(feedbackEntity));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(feedbackMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        TestCase.assertNull(feedbackMapper.mapEntityToDomain(null));
    }
}