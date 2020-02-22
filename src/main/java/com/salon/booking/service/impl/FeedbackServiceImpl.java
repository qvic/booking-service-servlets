package com.salon.booking.service.impl;

import com.salon.booking.dao.FeedbackDao;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.entity.UserEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.validator.Validator;

import java.util.List;

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
        return feedbackDao.findAllByWorkerId(workerId, properties)
                .map(feedbackMapper::mapEntityToDomain);
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
    public void saveFeedback(Integer workerId, String text) {
        feedbackTextValidator.validate(text);

        List<Order> finishedOrders = orderService.findLastFinishedOrders();
        boolean canLeaveFeedback = finishedOrders.stream()
                .anyMatch(o -> workerId.equals(o.getWorker().getId()));

        if (canLeaveFeedback) {
            UserEntity worker = UserEntity.builder()
                    .setId(workerId)
                    .build();
            FeedbackEntity feedback = FeedbackEntity.builder()
                    .setWorker(worker)
                    .setText(text)
                    .setStatus(FeedbackStatusEntity.CREATED)
                    .build();

            feedbackDao.save(feedback);
        }
    }

}
