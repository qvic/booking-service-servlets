package com.salon.booking.dao;

import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.entity.FeedbackEntity;
import com.salon.booking.entity.FeedbackStatusEntity;

public interface FeedbackDao extends PageableCrudDao<FeedbackEntity> {

    Page<FeedbackEntity> findAllApprovedWithWorkerId(Integer workerId, PageProperties properties);

    Page<FeedbackEntity> findAllByClientId(Integer clientId, PageProperties properties);

    Page<FeedbackEntity> findAllByStatus(FeedbackStatusEntity status, PageProperties properties);

    void updateStatus(Integer feedbackId, FeedbackStatusEntity feedbackStatusEntity);
}
