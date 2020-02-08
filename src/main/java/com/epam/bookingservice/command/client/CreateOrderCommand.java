package com.epam.bookingservice.command.client;

import com.epam.bookingservice.command.GetAndPostCommand;
import com.epam.bookingservice.command.exception.InvalidRequestParameterException;
import com.epam.bookingservice.domain.Order;
import com.epam.bookingservice.domain.Service;
import com.epam.bookingservice.domain.Timeslot;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.service.TimeslotService;
import com.epam.bookingservice.utility.PageUtility;
import com.epam.bookingservice.utility.ParseUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.epam.bookingservice.utility.PageUtility.*;

public class CreateOrderCommand implements GetAndPostCommand {

    private final TimeslotService timeslotService;

    public CreateOrderCommand(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<User> user = getUserFromSession(request);
        int timeslotId = ParseUtility.parseIntOrThrow(request.getParameter("timeslot-id"),
                () -> new InvalidRequestParameterException("timeslot-id is a required parameter"));

        int workerId = ParseUtility.parseIntOrThrow(request.getParameter("worker-id"),
                () -> new InvalidRequestParameterException("worker-id is a required parameter"));

        int serviceId = ParseUtility.parseIntOrThrow(request.getParameter("service-id"),
                () -> new InvalidRequestParameterException("worker-id is a required parameter"));

        timeslotService.updateTimeslotWithOrder(new Timeslot(timeslotId, null, null, null, // todo builder
                Order.builder()
                        .setDate(LocalDateTime.now())
                        .setClient(user.orElseThrow(() -> new RuntimeException("No user in the session"))) // todo refactor
                        .setWorker(User.builder()
                                .setId(workerId)
                                .build())
                        .setService(new Service(serviceId, null, null, null)) // todo builder
                        .build()
        ));

        response.sendRedirect("/app/client/orders");
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(getViewPathByName("create-order"), request, response);
    }
}
