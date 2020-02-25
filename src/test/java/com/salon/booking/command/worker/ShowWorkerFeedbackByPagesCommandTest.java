package com.salon.booking.command.worker;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.command.admin.ShowUnapprovedFeedbackCommand;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.FeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowWorkerFeedbackByPagesCommandTest extends AbstractCommandTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private ShowWorkerFeedbackByPagesCommand command;

    @Test
    public void processGetShouldForwardFeedbackPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("worker/feedback")))).thenReturn(requestDispatcher);
        PageProperties properties = new PageProperties(0, 1);
        Page<Feedback> page = new Page<>(Collections.emptyList(), properties, 0);
        when(feedbackService.findAllByWorkerId(eq(1), any())).thenReturn(page);

        when(request.getSession(eq(false))).thenReturn(session);
        User worker = User.builder().setId(1).setRole(Role.WORKER).build();
        when(session.getAttribute(eq("user"))).thenReturn(worker);

        command.processGet(request, response);

        verify(request).setAttribute(eq("page"), eq(page));
        verify(requestDispatcher).forward(request, response);
    }
}