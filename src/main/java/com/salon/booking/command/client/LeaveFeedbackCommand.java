package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.OrderService;
import com.salon.booking.utility.ParseUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static com.salon.booking.utility.RequestUtility.getRequiredIntParameter;
import static com.salon.booking.utility.RequestUtility.getRequiredStringParameter;

public class LeaveFeedbackCommand implements GetAndPostCommand {

    private static final Period FEEDBACK_THRESHOLD = Period.ofDays(7);

    private final FeedbackService feedbackService;
    private final OrderService orderService;

    public LeaveFeedbackCommand(FeedbackService feedbackService, OrderService orderService) {
        this.feedbackService = feedbackService;
        this.orderService = orderService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("order-id");
        ParseUtility.parseInt(orderId)
                .ifPresent(i -> request.setAttribute("selectedOrderId", i));

        User user = getUserWithRoleFromSession(request, Role.CLIENT);
        List<Order> orders = orderService.findFinishedOrdersAfter(getMinimalOrderEndTimeToLeaveFeedback(), user.getId());
        request.setAttribute("orders", orders);

        forward(getViewPathByName("client/leave-feedback"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getUserWithRoleFromSession(request, Role.CLIENT);
        String text = getRequiredStringParameter("text", request);
        int orderId = getRequiredIntParameter("order-id", request);

        feedbackService.saveFeedback(user.getId(), orderId, text, getMinimalOrderEndTimeToLeaveFeedback());

        redirect("/app/client/feedback", request, response);
    }

    private LocalDateTime getMinimalOrderEndTimeToLeaveFeedback() {
        return LocalDateTime.now().minus(FEEDBACK_THRESHOLD);
    }
}
