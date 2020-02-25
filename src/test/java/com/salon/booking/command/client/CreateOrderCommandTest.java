package com.salon.booking.command.client;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.Service;
import com.salon.booking.domain.Timeslot;
import com.salon.booking.domain.User;
import com.salon.booking.entity.OrderEntity;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.TimeService;
import com.salon.booking.service.TimeslotService;
import com.salon.booking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateOrderCommandTest extends AbstractCommandTest {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/orders";

    @Mock
    private OrderService orderService;

    @Mock
    private TimeslotService timeslotService;

    @Mock
    private UserService userService;

    @Mock
    private TimeService timeService;

    @InjectMocks
    private CreateOrderCommand command;

    @Test
    public void processGetShouldForwardToCreateOrderPage() throws ServletException, IOException {
        Timeslot timeslot = Timeslot.builder().build();
        Service service = Service.builder().build();
        User worker = User.builder().build();

        when(request.getRequestDispatcher(eq(getViewPathByName("client/create-order")))).thenReturn(requestDispatcher);
        when(request.getSession(eq(false))).thenReturn(session);

        when(session.getAttribute(eq("timeslotId"))).thenReturn(1);
        when(session.getAttribute(eq("serviceId"))).thenReturn(2);
        when(session.getAttribute(eq("workerId"))).thenReturn(3);

        when(timeslotService.findById(eq(1))).thenReturn(Optional.of(timeslot));
        when(orderService.findServiceById(eq(2))).thenReturn(Optional.of(service));
        when(userService.findById(eq(3))).thenReturn(Optional.of(worker));

        command.processGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("timeslot"), eq(timeslot));
        verify(request).setAttribute(eq("service"), eq(service));
        verify(request).setAttribute(eq("worker"), eq(worker));
    }

    @Test
    public void processPostShouldCallOrderServiceToSaveTheOrder() throws ServletException, IOException {
        when(request.getSession(eq(false))).thenReturn(session);
        when(session.getAttribute(eq("timeslotId"))).thenReturn(1);
        when(session.getAttribute(eq("serviceId"))).thenReturn(2);
        when(session.getAttribute(eq("workerId"))).thenReturn(3);

        User client = User.builder().setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);

        LocalDateTime now = LocalDateTime.now();
        when(timeService.getCurrentDateTime()).thenReturn(now);

        command.processPost(request, response);

        Order order = Order.builder()
                .setDate(now)
                .setService(Service.builder().setId(2).build())
                .setWorker(User.builder().setId(3).build())
                .setClient(client)
                .build();
        verify(orderService).saveOrderUpdatingTimeslots(eq(1), eq(order));
    }

    @Test
    public void processPostShouldForwardWhenOrderIsIncomplete() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/create-order")))).thenReturn(requestDispatcher);

        when(request.getSession(eq(false))).thenReturn(session);
        when(session.getAttribute(eq("timeslotId"))).thenReturn(1);
        when(session.getAttribute(eq("serviceId"))).thenReturn(null);
        when(session.getAttribute(eq("workerId"))).thenReturn(3);

        User client = User.builder().setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);

        command.processPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}