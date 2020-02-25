package com.salon.booking.command.client;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.TimeService;
import com.salon.booking.service.TimeslotService;
import com.salon.booking.service.UserService;
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
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectWorkerCommandTest extends AbstractCommandTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SelectWorkerCommand command;

    @After
    public void resetMocks() {
        Mockito.reset(userService);
    }

    @Test
    public void processGetShouldForwardWorkersPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/workers")))).thenReturn(requestDispatcher);
        PageProperties properties = new PageProperties(0, 1);
        Page<User> page = new Page<>(Collections.emptyList(), properties, 0);
        when(userService.findAllWorkers(any())).thenReturn(page);

        command.processGet(request, response);

        verify(request).setAttribute(eq("page"), eq(page));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void processPostShouldRedirectToCreateOrderPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/worker")))).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getParameter("worker-id")).thenReturn("7");

        command.processPost(request, response);

        verify(session).setAttribute(eq("workerId"), eq(7));
        verify(response).sendRedirect(eq(getFullUrl("/app/client/order-timeslot", request)));
    }
}