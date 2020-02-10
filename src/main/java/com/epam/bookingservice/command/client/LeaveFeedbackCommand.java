package com.epam.bookingservice.command.client;

import com.epam.bookingservice.command.PostCommand;
import com.epam.bookingservice.service.FeedbackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LeaveFeedbackCommand implements PostCommand {

    private final FeedbackService feedbackService;

    public LeaveFeedbackCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
