package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.FeedbackEntity;
import com.epam.bookingservice.entity.FeedbackStatusEntity;

import java.util.List;

public interface FeedbackDao extends PageableCrudDao<FeedbackEntity> {

    List<FeedbackEntity> findAllByWorkerId(Integer id);

    List<FeedbackEntity> findAllByStatus(FeedbackStatusEntity status);
}
