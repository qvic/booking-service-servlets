package com.bookingservice.command.client;

import com.bookingservice.utility.RequestUtility;
import com.bookingservice.command.GetAndPostCommand;
import com.bookingservice.domain.Service;
import com.bookingservice.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.bookingservice.utility.PageUtility.getViewPathByName;

public class SelectServiceCommand implements GetAndPostCommand {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/order-worker";
    private final OrderService orderService;

    public SelectServiceCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Service> services = orderService.findAllServices();
        request.setAttribute("services", services);
        forward(getViewPathByName("services"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int serviceId = RequestUtility.getRequiredIntParameter("service-id", request);
        request.getSession().setAttribute("serviceId", serviceId);

        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }
}
