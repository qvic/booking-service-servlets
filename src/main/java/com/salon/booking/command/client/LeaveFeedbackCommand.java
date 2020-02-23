package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.Order;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.OrderService;
import com.salon.booking.utility.PageUtility;
import com.salon.booking.utility.RequestUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.salon.booking.utility.PageUtility.*;

public class LeaveFeedbackCommand implements GetAndPostCommand {

    private final FeedbackService feedbackService;
    private final OrderService orderService;

    public LeaveFeedbackCommand(FeedbackService feedbackService, OrderService orderService) {
        this.feedbackService = feedbackService;
        this.orderService = orderService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = orderService.findLastFinishedOrders();
        request.setAttribute("orders", orders);

        forward(getViewPathByName("client/leave-feedback"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = RequestUtility.getRequiredStringParameter("text", request);
        int workerId = RequestUtility.getRequiredIntParameter("worker-id", request);

        feedbackService.saveFeedback(workerId, text);
    }
}
