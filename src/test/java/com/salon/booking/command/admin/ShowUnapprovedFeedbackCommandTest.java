package com.salon.booking.command.admin;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowUnapprovedFeedbackCommandTest extends AbstractCommandTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private ShowUnapprovedFeedbackCommand command;

    @Test
    public void processGetShouldCallUserService() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("admin/feedback")))).thenReturn(requestDispatcher);
        PageProperties properties = new PageProperties(0, 1);
        Page<Feedback> page = new Page<>(Collections.emptyList(), properties, 0);
        when(feedbackService.findAllByStatus(eq(FeedbackStatus.CREATED), any())).thenReturn(page);

        command.processGet(request, response);

        verify(request).setAttribute(eq("page"), eq(page));
        verify(requestDispatcher).forward(request, response);
    }
}