package com.salon.booking.command.client;

import com.salon.booking.command.GetCommand;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.FeedbackService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class ShowFeedbackCommand implements GetCommand {

    private static final int DEFAULT_FEEDBACK_PER_PAGE = 10;

    private final FeedbackService feedbackService;

    public ShowFeedbackCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User client = getUserWithRoleFromSession(request, Role.CLIENT);
        PageProperties pageProperties = getPageProperties(DEFAULT_FEEDBACK_PER_PAGE, request);

        Page<Feedback> approvedFeedbackPage = feedbackService.findAllByClientId(client.getId(), pageProperties);
        request.setAttribute("page", approvedFeedbackPage);

        forward(getViewPathByName("client/feedback"), request, response);
    }
}
