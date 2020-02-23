package com.salon.booking.mapper;

import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.Order;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.OrderEntity;
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
public class FeedbackMapperTest {

    @Mock
    private Mapper<OrderEntity, Order> orderMapper;

    @Mock
    private Mapper<FeedbackStatusEntity, FeedbackStatus> feedbackStatusMapper;

    private FeedbackMapper feedbackMapper;

    @Before
    public void injectMocks() {
        feedbackMapper = new FeedbackMapper(orderMapper, feedbackStatusMapper);
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

        Feedback feedback = Feedback.builder()
                .setId(1)
                .setText("text")
                .setOrder(order)
                .build();

        FeedbackEntity expectedFeedbackEntity = FeedbackEntity.builder()
                .setId(1)
                .setStatus(FeedbackStatusEntity.CREATED)
                .setText("text")
                .setOrder(orderEntity)
                .build();

        assertEquals(expectedFeedbackEntity, feedbackMapper.mapDomainToEntity(feedback));
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
        when(feedbackStatusMapper.mapEntityToDomain(eq(FeedbackStatusEntity.APPROVED))).thenReturn(FeedbackStatus.APPROVED);

        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
                .setId(1)
                .setStatus(FeedbackStatusEntity.APPROVED)
                .setText("text")
                .setOrder(orderEntity)
                .build();

        Feedback expectedFeedback = Feedback.builder()
                .setId(1)
                .setText("text")
                .setOrder(order)
                .setStatus(FeedbackStatus.APPROVED)
                .build();

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