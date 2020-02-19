package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Service;
import com.salon.booking.domain.User;
import com.salon.booking.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class CreateOrderCommand implements GetAndPostCommand {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/orders";

    private final OrderService orderService;

    public CreateOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(getViewPathByName("create-order"),
                request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getUserFromSession(request);

        Integer timeslotId = (Integer) request.getSession().getAttribute("timeslotId");
        Integer serviceId = (Integer) request.getSession().getAttribute("serviceId");
        Integer workerId = (Integer) request.getSession().getAttribute("workerId");

        if (timeslotId == null || serviceId == null || workerId == null) { // todo specialized method
            forwardWithMessage(getViewPathByName("create-order"), "Please, select all parameters", // todo pass key for i18n
                    request, response);
            return;
        }

        orderService.saveOrderUpdatingTimeslots(timeslotId,
                Order.builder()
                        .setDate(LocalDateTime.now())
                        .setClient(user)
                        .setWorker(User.builder()
                                .setId(workerId)
                                .build())
                        .setService(Service.builder()
                                .setId(serviceId)
                                .build())
                        .build());

        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }


}
