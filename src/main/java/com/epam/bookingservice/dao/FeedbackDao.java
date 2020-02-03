package com.epam.bookingservice.dao;

import com.epam.bookingservice.entity.Feedback;
import com.epam.bookingservice.entity.FeedbackStatus;

import java.util.List;

public interface FeedbackDao extends PageableCrudDao<Feedback> {

    List<Feedback> findAllByWorkerId(Integer id);

    List<Feedback> findAllByStatus(FeedbackStatus status);
}
