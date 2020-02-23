package com.salon.booking.command.admin;

import com.salon.booking.command.PostCommand;
import com.salon.booking.service.FeedbackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.salon.booking.utility.RequestUtility.getRequiredIntParameter;

public class ApproveFeedbackCommand implements PostCommand {

    private final FeedbackService feedbackService;

    public ApproveFeedbackCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int feedbackId = getRequiredIntParameter("feedback-id", request);
        feedbackService.approveFeedbackById(feedbackId);

        response.sendRedirect("/app/admin/feedback");
    }
}
