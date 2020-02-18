package com.bookingservice.service;

import com.bookingservice.domain.Feedback;
import com.bookingservice.domain.FeedbackStatus;

import java.util.List;

public interface FeedbackService {

    List<Feedback> findAllByWorkerId(Integer workerId);

    List<Feedback> findAllByStatus(FeedbackStatus status);
}
