package com.salon.booking.command.admin;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.command.exception.InvalidRequestParameterException;
import com.salon.booking.service.FeedbackService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApproveFeedbackCommandTest extends AbstractCommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private ApproveFeedbackCommand command;

    @Test
    public void processPostShouldCallFeedbackService() throws ServletException, IOException {
        when(request.getParameter("feedback-id")).thenReturn("1");

        command.processPost(request, response);

        verify(feedbackService).approveFeedbackById(eq(1));
    }

    @Test
    public void processPostShouldThrowExceptionWhenFeedbackIdWasNotFoundInParameters() throws ServletException, IOException {
        when(request.getParameter("feedback-id")).thenReturn(null);

        expectedException.expect(InvalidRequestParameterException.class);
        command.processPost(request, response);
    }
}