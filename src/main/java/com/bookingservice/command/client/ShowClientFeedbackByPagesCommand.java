package com.bookingservice.command.client;

import com.bookingservice.command.GetCommand;
import com.bookingservice.service.FeedbackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowClientFeedbackByPagesCommand implements GetCommand {

    private final FeedbackService feedbackService;

    public ShowClientFeedbackByPagesCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
