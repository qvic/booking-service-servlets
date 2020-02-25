package com.salon.booking.command.admin;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.command.exception.InvalidRequestParameterException;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PromoteClientToWorkerCommandTest extends AbstractCommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserService userService;

    @InjectMocks
    private PromoteClientToWorkerCommand command;

    @Test
    public void processPostShouldCallUserService() throws ServletException, IOException {
        when(request.getParameter("client-id")).thenReturn("1");

        command.processPost(request, response);

        verify(userService).promoteToWorker(eq(1));
    }

    @Test
    public void processPostShouldThrowExceptionWhenClientIdWasNotFoundInParameters() throws ServletException, IOException {
        when(request.getParameter("client-id")).thenReturn(null);

        expectedException.expect(InvalidRequestParameterException.class);
        command.processPost(request, response);
    }
}