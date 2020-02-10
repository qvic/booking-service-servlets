package com.epam.bookingservice.command.client;

import com.epam.bookingservice.command.GetCommand;
import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;

public class ShowOrdersCommand implements GetCommand {

    private final OrderService orderService;

    public ShowOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getUserFromSession(request);
        List<Order> orders = orderService.findAllByClient(user);
        request.setAttribute("orders", orders);
        forward(getViewPathByName("client-orders"), request, response);
    }
}
