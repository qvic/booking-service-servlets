package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Service;
import com.salon.booking.domain.User;
import com.salon.booking.service.OrderService;
import com.salon.booking.utility.RequestUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class CreateOrderCommand implements GetAndPostCommand {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/orders";

    private final OrderService orderService;

    public CreateOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(getViewPathByName("client/create-order"),
                request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Integer> timeslotId = RequestUtility.getIntSessionAttribute("timeslotId", request);
        Optional<Integer> serviceId = RequestUtility.getIntSessionAttribute("serviceId", request);
        Optional<Integer> workerId = RequestUtility.getIntSessionAttribute("workerId", request);

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
