package com.epam.bookingservice.service;

import com.epam.bookingservice.domain.Feedback;
import com.epam.bookingservice.domain.FeedbackStatus;

import java.util.List;

public interface FeedbackService {

    List<Feedback> findAllByWorkerId(Integer workerId);

    List<Feedback> findAllByStatus(FeedbackStatus status);
}
