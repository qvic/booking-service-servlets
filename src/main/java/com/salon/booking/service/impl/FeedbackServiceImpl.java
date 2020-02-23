package com.salon.booking.service.impl;

import com.salon.booking.dao.FeedbackDao;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.validator.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackDao feedbackDao;
    private final OrderService orderService;
    private final Mapper<FeedbackEntity, Feedback> feedbackMapper;
    private final Mapper<FeedbackStatusEntity, FeedbackStatus> feedbackStatusMapper;
    private final Validator<String> feedbackTextValidator;

    public FeedbackServiceImpl(FeedbackDao feedbackDao, OrderService orderService, Mapper<FeedbackEntity, Feedback> feedbackMapper, Mapper<FeedbackStatusEntity, FeedbackStatus> feedbackStatusMapper, Validator<String> feedbackTextValidator) {
        this.feedbackDao = feedbackDao;
        this.orderService = orderService;
        this.feedbackMapper = feedbackMapper;
        this.feedbackStatusMapper = feedbackStatusMapper;
        this.feedbackTextValidator = feedbackTextValidator;
    }

    @Override
    public Page<Feedback> findAllByWorkerId(Integer workerId, PageProperties properties) {
        return feedbackDao.findAllApprovedWithWorkerId(workerId, properties)
                .map(this::buildFeedbackWithOrder);
    }

    @Override
    public Page<Feedback> findAllByClientId(Integer clientId, PageProperties properties) {
        return feedbackDao.findAllByClientId(clientId, properties)
                .map(this::buildFeedbackWithOrder);
    }

    private Feedback buildFeedbackWithOrder(FeedbackEntity feedbackEntity) {
        Feedback feedback = feedbackMapper.mapEntityToDomain(feedbackEntity);

        Integer orderId = feedbackEntity.getOrder().getId();
        Order order = orderService.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        return Feedback.builder(feedback)
                .setOrder(order)
                .build();
    }

    @Override
    public Page<Feedback> findAllByStatus(FeedbackStatus status, PageProperties properties) {
        return feedbackDao.findAllByStatus(feedbackStatusMapper.mapDomainToEntity(status), properties)
                .map(feedbackMapper::mapEntityToDomain);
    }

    @Override
    public void approveFeedbackById(Integer feedbackId) {
        feedbackDao.updateStatus(feedbackId, FeedbackStatusEntity.APPROVED);
    }

    @Override
    public void saveFeedback(Integer clientId, Integer orderId, String text, LocalDateTime minOrderEndTime) {
        feedbackTextValidator.validate(text);

        boolean canLeaveFeedback = canLeaveFeedbackAbout(orderId, clientId, minOrderEndTime);

        if (canLeaveFeedback) {
            OrderEntity order = OrderEntity.builder()
                    .setId(orderId)
                    .build();
            FeedbackEntity feedback = FeedbackEntity.builder()
                    .setOrder(order)
                    .setText(text)
                    .setStatus(FeedbackStatusEntity.CREATED)
                    .build();

            feedbackDao.save(feedback);
        }
    }

    private boolean canLeaveFeedbackAbout(Integer orderId, Integer clientId, LocalDateTime minTime) {
        List<Order> finishedOrders = orderService.findFinishedOrdersAfter(minTime, clientId);
        return finishedOrders.stream()
                .anyMatch(o -> orderId.equals(o.getId()));
    }

}
