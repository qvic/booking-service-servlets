package com.salon.booking.mapper;

import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.User;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.UserEntity;
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
    private Mapper<UserEntity, User> userMapper;

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

        Feedback feedback = new Feedback(1, "text", user);

        FeedbackEntity expectedFeedbackEntity = FeedbackEntity.builder()
                .setId(1)
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
                .setId(1)
                .setStatus(FeedbackStatusEntity.CREATED)
                .setText("text")
                .setWorker(userEntity)
                .build();

        Feedback expectedFeedback = new Feedback(1, "text", user);

        assertEquals(expectedFeedback, feedbackMapper.mapEntityToDomain(feedbackEntity));
    }

    @Test
    public void mapDomainToEntityShouldReturnNullOnNullParameter() {
        assertNull(feedbackMapper.mapDomainToEntity(null));
    }

    @Test
    public void mapEntityToDomainShouldReturnNullOnNullParameter() {
        assertNull(feedbackMapper.mapEntityToDomain(null));
    }
}