package com.salon.booking.service;

import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;

public interface FeedbackService {

    Page<Feedback> findAllByWorkerId(Integer workerId, PageProperties properties);

    Page<Feedback> findAllByStatus(FeedbackStatus status, PageProperties properties);

    void approveFeedbackById(Integer feedbackId);
}
