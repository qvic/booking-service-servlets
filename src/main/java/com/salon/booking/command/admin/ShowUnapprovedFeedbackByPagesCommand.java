package com.salon.booking.command.admin;

import com.salon.booking.command.GetCommand;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.FeedbackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class ShowUnapprovedFeedbackByPagesCommand implements GetCommand {

    private static final int DEFAULT_FEEDBACK_PER_PAGE = 20;
    private final FeedbackService feedbackService;

    public ShowUnapprovedFeedbackByPagesCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageProperties pageProperties = getPageProperties(DEFAULT_FEEDBACK_PER_PAGE, request);
        Page<Feedback> feedbackPage = feedbackService.findAllByStatus(FeedbackStatus.CREATED, pageProperties);
        request.setAttribute("page", feedbackPage);

        forward(getViewPathByName("admin/feedback-list"), request, response);
    }
}
