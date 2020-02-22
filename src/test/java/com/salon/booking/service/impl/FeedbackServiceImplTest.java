package com.salon.booking.service.impl;

import com.salon.booking.dao.FeedbackDao;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackServiceImplTest {

    @Mock
    private FeedbackDao feedbackDao;

    @Mock
    private OrderService orderService;

    @Mock
    private Mapper<FeedbackEntity, Feedback> feedbackMapper;

    @Mock
    private Validator<String> feedbackTextValidator;

    @Mock
    private Mapper<FeedbackStatusEntity, FeedbackStatus> feedbackStatusMapper;

    private FeedbackServiceImpl feedbackService;

    @Before
    public void injectMocks() {
        feedbackService = new FeedbackServiceImpl(feedbackDao, orderService, feedbackMapper, feedbackStatusMapper, feedbackTextValidator);
    }

    @Test
    public void findAllByWorkerId() {
        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
                .setId(1)
                .setText("test")
                .setWorker(UserEntity.builder()
                        .setId(23)
                        .build())
                .build();

        Feedback feedback = new Feedback(1, "test", null);

        PageProperties properties = new PageProperties(0, 1);
        Page<FeedbackEntity> feedbackEntityPage = new Page<>(Collections.singletonList(feedbackEntity),
                properties, 1);

        when(feedbackDao.findAllByWorkerId(eq(23), eq(properties))).thenReturn(feedbackEntityPage);
        when(feedbackMapper.mapEntityToDomain(eq(feedbackEntity))).thenReturn(feedback);

        Page<Feedback> allByWorkerId = feedbackService.findAllByWorkerId(23, properties);

        assertThat(allByWorkerId.getItems(), equalTo(Collections.singletonList(feedback)));
    }

    @Test
    public void findAllByStatus() {
        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
                .setId(1)
                .setText("test")
                .setStatus(FeedbackStatusEntity.CREATED)
                .build();

        Feedback feedback = new Feedback(1, "test", null);

        PageProperties properties = new PageProperties(0, 1);
        Page<FeedbackEntity> feedbackEntityPage = new Page<>(Collections.singletonList(feedbackEntity),
                properties, 1);

        when(feedbackDao.findAllByStatus(eq(FeedbackStatusEntity.CREATED), eq(properties))).thenReturn(feedbackEntityPage);
        when(feedbackMapper.mapEntityToDomain(eq(feedbackEntity))).thenReturn(feedback);
        when(feedbackStatusMapper.mapDomainToEntity(eq(FeedbackStatus.CREATED))).thenReturn(FeedbackStatusEntity.CREATED);

        Page<Feedback> allByStatus = feedbackService.findAllByStatus(FeedbackStatus.CREATED, properties);

        assertThat(allByStatus.getItems(), equalTo(Collections.singletonList(feedback)));
    }

    @Test
    public void approveFeedbackById() {
        feedbackService.approveFeedbackById(123);

        verify(feedbackDao).updateStatus(eq(123), eq(FeedbackStatusEntity.APPROVED));
    }
}