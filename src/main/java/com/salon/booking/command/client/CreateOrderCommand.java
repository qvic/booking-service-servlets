package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Service;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.User;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.TimeslotService;
import com.salon.booking.service.UserService;
import com.salon.booking.utility.RequestUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static com.salon.booking.utility.RequestUtility.*;

public class CreateOrderCommand implements GetAndPostCommand {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/orders";

    private final OrderService orderService;
    private final TimeslotService timeslotService;
    private final UserService userService;

    public CreateOrderCommand(OrderService orderService, TimeslotService timeslotService, UserService userService) {
        this.orderService = orderService;
        this.timeslotService = timeslotService;
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setOrderAttributes(request);

        forward(getViewPathByName("client/create-order"),
                request, response);
    }

    private void setOrderAttributes(HttpServletRequest request) {
        getIntSessionAttribute("timeslotId", request)
                .flatMap(timeslotService::findById)
                .ifPresent(timeslot -> request.setAttribute("timeslot", timeslot));

        getIntSessionAttribute("serviceId", request)
                .flatMap(orderService::findServiceById)
                .ifPresent(service -> request.setAttribute("service", service));

        getIntSessionAttribute("workerId", request)
                .flatMap(userService::findById)
                .ifPresent(worker -> request.setAttribute("worker", worker));
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Integer> timeslotId = getIntSessionAttribute("timeslotId", request);
        Optional<Integer> serviceId = getIntSessionAttribute("serviceId", request);
        Optional<Integer> workerId = getIntSessionAttribute("workerId", request);

        if (!timeslotId.isPresent() || !serviceId.isPresent() || !workerId.isPresent()) {
            forwardWithMessage(getViewPathByName("client/create-order"), "message.select_all_parameters",
                    request, response);
            return;
        }

        User client = getUserFromSession(request);
        Order order = buildNewOrder(client, workerId.get(), serviceId.get());
        orderService.saveOrderUpdatingTimeslots(timeslotId.get(), order);

        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }

    private Order buildNewOrder(User client, Integer workerId, Integer serviceId) {
        User worker = User.builder()
                .setId(workerId)
                .build();

        Service service = Service.builder()
                .setId(serviceId)
                .build();

        return Order.builder()
                .setDate(LocalDateTime.now())
                .setClient(client)
                .setWorker(worker)
                .setService(service)
                .build();
    }
}
