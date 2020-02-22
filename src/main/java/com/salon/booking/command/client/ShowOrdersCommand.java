package com.salon.booking.command.client;

import com.salon.booking.command.GetCommand;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.User;
import com.salon.booking.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class ShowOrdersCommand implements GetCommand {

    private final OrderService orderService;

    public ShowOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getUserFromSession(request);
        List<Order> orders = orderService.findAllByClientId(user.getId());
        request.setAttribute("orders", orders);

        forward(getViewPathByName("client/orders"), request, response);
    }
}
