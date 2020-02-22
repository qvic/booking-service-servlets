package com.salon.booking.command.client;

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

public class ShowClientFeedbackByPagesCommand implements GetCommand {

    private static final int DEFAULT_FEEDBACK_PER_PAGE = 10;

    private final FeedbackService feedbackService;

    public ShowClientFeedbackByPagesCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumber = request.getParameter("page");
        String itemsPerPage = request.getParameter("limit");

        PageProperties pageProperties = PageProperties.buildByParameters(pageNumber, itemsPerPage, DEFAULT_FEEDBACK_PER_PAGE);

        Page<Feedback> approvedFeedbackPage = feedbackService.findAllByStatus(FeedbackStatus.APPROVED, pageProperties);
        request.setAttribute("page", approvedFeedbackPage);

        forward(getViewPathByName("client/feedback"), request, response);
    }
}
