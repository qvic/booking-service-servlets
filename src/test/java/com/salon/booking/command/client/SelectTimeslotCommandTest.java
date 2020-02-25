package com.salon.booking.command.client;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.service.TimeService;
import com.salon.booking.service.TimeslotService;
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
public class SelectTimeslotCommandTest extends AbstractCommandTest {

    @Mock
    private TimeslotService timeslotService;

    @Mock
    private TimeService timeService;

    @InjectMocks
    private SelectTimeslotCommand showTimetablesCommand;

    @After
    public void resetMocks() {
        Mockito.reset(timeslotService, timeService);
    }

    @Test
    public void processGetShouldForwardTimetablesForSelectedServiceAndWorker() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/timetables")))).thenReturn(requestDispatcher);
        when(request.getSession(eq(false))).thenReturn(session);

        User client = User.builder().setId(1).setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);
        when(session.getAttribute(eq("serviceId"))).thenReturn(5);
        when(session.getAttribute(eq("workerId"))).thenReturn(6);

        when(timeslotService.findTimetablesForOrderWith(eq(5), eq(6), any())).thenReturn(Collections.emptyList());

        showTimetablesCommand.processGet(request, response);

        verify(request).setAttribute(eq("timetables"), eq(Collections.emptyList()));
        verify(requestDispatcher).forward(any(), any());
    }

    @Test
    public void processGetShouldRedirectWhenServiceIsMissingInSession() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/timetables")))).thenReturn(requestDispatcher);
        when(request.getSession(eq(false))).thenReturn(session);
        when(request.getContextPath()).thenReturn("");

        User client = User.builder().setId(1).setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);
        when(session.getAttribute(eq("serviceId"))).thenReturn(null);
        when(session.getAttribute(eq("workerId"))).thenReturn(6);

        showTimetablesCommand.processGet(request, response);

        verify(response).sendRedirect(eq(getFullUrl("/app/client/order-service", request)));
    }

    @Test
    public void processPostShouldRedirectToCreateOrderPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/timetables")))).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        when(request.getParameter("timeslot-id")).thenReturn("7");

        showTimetablesCommand.processPost(request, response);

        verify(session).setAttribute(eq("timeslotId"), eq(7));
        verify(response).sendRedirect(eq(getFullUrl("/app/client/create-order", request)));
    }
}