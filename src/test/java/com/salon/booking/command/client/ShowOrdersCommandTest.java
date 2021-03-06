package com.salon.booking.command.client;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.command.worker.ShowWorkerFeedbackByPagesCommand;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.Order;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.OrderService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowOrdersCommandTest extends AbstractCommandTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private ShowOrdersCommand command;

    @After
    public void resetMocks() {
        Mockito.reset(orderService);
    }

    @Test
    public void processGetShouldForwardOrdersPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/orders")))).thenReturn(requestDispatcher);
        when(request.getSession(eq(false))).thenReturn(session);

        User client = User.builder().setId(1).setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);

        List<Order> orders = Collections.emptyList();
        when(orderService.findAllByClientId(eq(1))).thenReturn(orders);

        command.processGet(request, response);

        verify(request).setAttribute(eq("orders"), eq(orders));
        verify(requestDispatcher).forward(request, response);
    }
}