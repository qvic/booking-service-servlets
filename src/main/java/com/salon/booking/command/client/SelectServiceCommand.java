package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.Service;
import com.salon.booking.service.OrderService;
import com.salon.booking.utility.RequestUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

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

        forward(getViewPathByName("client/services"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int serviceId = RequestUtility.getRequiredIntParameter("service-id", request);
        request.getSession().setAttribute("serviceId", serviceId);
        clearCart(request);

        redirect(REDIRECT_AFTER_SUBMIT, request, response);
    }

    private void clearCart(HttpServletRequest request) {
        RequestUtility.removeSessionAttribute("timeslotId", request);
        RequestUtility.removeSessionAttribute("workerId", request);
    }
}
