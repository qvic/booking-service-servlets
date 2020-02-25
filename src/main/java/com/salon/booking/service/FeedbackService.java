package com.salon.booking.service;

import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;

import java.time.LocalDateTime;

public interface FeedbackService {

    Page<Feedback> findAllByWorkerId(Integer workerId, PageProperties properties);

    Page<Feedback> findAllByClientId(Integer clientId, PageProperties properties);

    Page<Feedback> findAllByStatus(FeedbackStatus status, PageProperties properties);

    void approveFeedbackById(Integer feedbackId);

    void saveFeedback(Integer clientId, Integer orderId, String text, LocalDateTime minOrderEndTime);
}
