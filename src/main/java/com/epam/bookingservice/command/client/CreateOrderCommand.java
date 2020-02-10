package com.epam.bookingservice.command.client;

import com.epam.bookingservice.command.GetAndPostCommand;
import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.service.TimeslotService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;

public class CreateOrderCommand implements GetAndPostCommand {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/orders";

    private final TimeslotService timeslotService;

    public CreateOrderCommand(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
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
            forwardWithMessage(getViewPathByName("create-order"), "Please, select all parameters",
                    request, response);
            return;
        }

        timeslotService.updateTimeslotWithOrder(Timeslot.builder()
                .setId(timeslotId)
                .setOrder(Order.builder()
                        .setDate(LocalDateTime.now())
                        .setClient(user)
                        .setWorker(User.builder()
                                .setId(workerId)
                                .build())
                        .setService(Service.builder()
                                .setId(serviceId)
                                .build())
                        .build())
                .build());

        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }
}
