package com.salon.booking.service.impl;

import com.salon.booking.dao.FeedbackDao;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;
import com.salon.booking.mapper.Mapper;
import com.salon.booking.service.FeedbackService;

public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackDao feedbackDao;
    private final Mapper<FeedbackEntity, Feedback> feedbackMapper;
    private final Mapper<FeedbackStatusEntity, FeedbackStatus> feedbackStatusMapper;

    public FeedbackServiceImpl(FeedbackDao feedbackDao, Mapper<FeedbackEntity, Feedback> feedbackMapper, Mapper<FeedbackStatusEntity, FeedbackStatus> feedbackStatusMapper) {
        this.feedbackDao = feedbackDao;
        this.feedbackMapper = feedbackMapper;
        this.feedbackStatusMapper = feedbackStatusMapper;
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
}
