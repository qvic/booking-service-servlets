package com.bookingservice.dao;

import com.bookingservice.domain.page.Page;
import com.bookingservice.domain.page.PageProperties;
import com.bookingservice.entity.FeedbackEntity;
import com.bookingservice.entity.FeedbackStatusEntity;

public interface FeedbackDao extends PageableCrudDao<FeedbackEntity> {

    Page<FeedbackEntity> findAllByWorkerId(Integer workerId, PageProperties properties);

    Page<FeedbackEntity> findAllByStatus(FeedbackStatusEntity status, PageProperties properties);

    void updateStatus(Integer feedbackId, FeedbackStatusEntity feedbackStatusEntity);
}
