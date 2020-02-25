package com.salon.booking.command.client;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.FeedbackService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowFeedbackCommandTest extends AbstractCommandTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private ShowFeedbackCommand command;

    @After
    public void resetMocks() {
        Mockito.reset(feedbackService);
    }

    @Test
    public void processGetShouldCallUserService() throws ServletException, IOException {
        when(request.getSession(eq(false))).thenReturn(session);

        User client = User.builder().setId(1).setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);
        when(request.getRequestDispatcher(eq(getViewPathByName("client/feedback")))).thenReturn(requestDispatcher);

        PageProperties properties = new PageProperties(0, 1);
        Page<Feedback> page = new Page<>(Collections.emptyList(), properties, 0);
        when(feedbackService.findAllByClientId(eq(1), any())).thenReturn(page);

        command.processGet(request, response);

        verify(request).setAttribute(eq("page"), eq(page));
        verify(requestDispatcher).forward(request, response);
    }
}