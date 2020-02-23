package com.salon.booking.command.worker;

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

public class ShowWorkerFeedbackByPagesCommand implements GetCommand {

    private static final long DEFAULT_ITEMS_PER_PAGE = 5;

    private final FeedbackService feedbackService;

    public ShowWorkerFeedbackByPagesCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getUserWithRoleFromSession(request, Role.WORKER);
        PageProperties pageProperties = getPageProperties(DEFAULT_ITEMS_PER_PAGE, request);

        Page<Feedback> page = feedbackService.findAllByWorkerId(user.getId(), pageProperties);
        request.setAttribute("page", page);

        forward(getViewPathByName("worker/feedback"), request, response);
    }
}
