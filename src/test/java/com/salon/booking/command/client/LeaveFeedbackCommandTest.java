package com.salon.booking.command.client;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.OrderService;
import com.salon.booking.service.TimeService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static com.salon.booking.utility.RequestUtility.getFullUrl;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LeaveFeedbackCommandTest extends AbstractCommandTest {

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private OrderService orderService;

    @Mock
    private TimeService timeService;

    @InjectMocks
    private LeaveFeedbackCommand command;

    @After
    public void resetMocks() {
        Mockito.reset(feedbackService, orderService, timeService);
    }

    @Test
    public void processGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/leave-feedback")))).thenReturn(requestDispatcher);
        when(request.getParameter("order-id")).thenReturn("5");
        when(request.getSession(eq(false))).thenReturn(session);
        User client = User.builder().setId(1).setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);
        when(orderService.findFinishedOrdersAfter(any(), eq(1))).thenReturn(Collections.emptyList());
        when(timeService.getCurrentDateTime()).thenReturn(LocalDateTime.now());

        command.processGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("selectedOrderId"), eq(5));
        verify(request).setAttribute(eq("orders"), eq(Collections.emptyList()));
    }

    @Test
    public void processPost() throws IOException, ServletException {
        when(request.getParameter("text")).thenReturn("text");
        when(request.getParameter("order-id")).thenReturn("5");
        when(request.getContextPath()).thenReturn("");

        when(request.getSession(eq(false))).thenReturn(session);
        User client = User.builder().setId(1).setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);
        when(timeService.getCurrentDateTime()).thenReturn(LocalDateTime.now());

        command.processPost(request, response);

        verify(feedbackService).saveFeedback(eq(1), eq(5), eq("text"), any());
        verify(response).sendRedirect(getFullUrl("/app/client/feedback", request));
    }
}