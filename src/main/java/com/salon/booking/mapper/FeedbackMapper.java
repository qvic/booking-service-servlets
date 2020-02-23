package com.salon.booking.mapper;

import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.Order;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.OrderEntity;

public class FeedbackMapper implements Mapper<FeedbackEntity, Feedback> {

    private final Mapper<OrderEntity, Order> orderMapper;
    private final Mapper<FeedbackStatusEntity, FeedbackStatus> feedbackStatusMapper;

    public FeedbackMapper(Mapper<OrderEntity, Order> orderMapper, Mapper<FeedbackStatusEntity, FeedbackStatus> feedbackStatusMapper) {
        this.orderMapper = orderMapper;
        this.feedbackStatusMapper = feedbackStatusMapper;
    }

    @Override
    public FeedbackEntity mapDomainToEntity(Feedback feedback) {
        if (feedback == null) {
            return null;
        }

        return FeedbackEntity.builder()
                .setId(feedback.getId())
                .setText(feedback.getText())
                .setOrder(orderMapper.mapDomainToEntity(feedback.getOrder()))
                .setStatus(FeedbackStatusEntity.CREATED)
                .build();
    }

    @Override
    public Feedback mapEntityToDomain(FeedbackEntity feedback) {
        if (feedback == null) {
            return null;
        }

        return Feedback.builder()
                .setId(feedback.getId())
                .setText(feedback.getText())
                .setOrder(orderMapper.mapEntityToDomain(feedback.getOrder()))
                .setStatus(feedbackStatusMapper.mapEntityToDomain(feedback.getStatus()))
                .build();
    }
}
