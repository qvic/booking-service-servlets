package com.salon.booking.command.user;

import com.salon.booking.command.AbstractCommandTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest extends AbstractCommandTest {

    @InjectMocks
    private LogoutCommand logoutCommand;

    @Test
    public void userShouldBeRedirectedWhenLoggedOutAndSessionIsNull() throws ServletException, IOException {
        when(request.getSession(eq(false))).thenReturn(null);

        logoutCommand.processGet(request, response);

        verify(response, times(1)).sendRedirect(anyString());
    }

    @Test
    public void userShouldBeRedirectedWhenLoggedOutAndSessionIsNotNull() throws ServletException, IOException {
        when(request.getSession(eq(false))).thenReturn(session);

        logoutCommand.processGet(request, response);

        verify(session, times(1)).invalidate();
        verify(response, times(1)).sendRedirect(anyString());
    }
}