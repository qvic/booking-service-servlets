package com.salon.booking.command.client;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
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

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static com.salon.booking.utility.RequestUtility.getFullUrl;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectServiceCommandTest extends AbstractCommandTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private SelectServiceCommand command;

    @After
    public void resetMocks() {
        Mockito.reset(orderService);
    }

    @Test
    public void processGetShouldForwardWorkersPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/services")))).thenReturn(requestDispatcher);
        when(orderService.findAllServices()).thenReturn(Collections.emptyList());

        command.processGet(request, response);

        verify(request).setAttribute(eq("services"), eq(Collections.emptyList()));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void processPostShouldRedirectToCreateOrderPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/services")))).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getParameter("service-id")).thenReturn("7");

        command.processPost(request, response);

        verify(session).setAttribute(eq("serviceId"), eq(7));
        verify(response).sendRedirect(eq(getFullUrl("/app/client/order-worker", request)));
    }
}