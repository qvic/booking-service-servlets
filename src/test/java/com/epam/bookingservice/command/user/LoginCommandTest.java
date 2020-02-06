package com.epam.bookingservice.command.user;

import com.epam.bookingservice.command.AbstractCommandTest;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.utility.PageUtility;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest extends AbstractCommandTest {

    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String INVALID_USER_EMAIL = "invalid_user_email";
    private static final String INVALID_USER_PASSWORD = "invalid_user_password";

    private static final User USER = User.builder()
            .setEmail(USER_EMAIL)
            .setPassword(USER_PASSWORD)
            .build();

    private static final String LOGIN_PAGE_PATH = PageUtility.getViewPathByName("login");

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginCommand loginCommand;

    @After
    public void resetMocks() {
        Mockito.reset(userService);
    }

    @Test
    public void userShouldBeRedirectedIfLoginWasSuccessfulAndSessionIsNull() throws ServletException, IOException {
        when(request.getParameter(eq("email"))).thenReturn(USER_EMAIL);
        when(request.getParameter(eq("password"))).thenReturn(USER_PASSWORD);

        when(request.getSession(eq(false))).thenReturn(null);
        when(request.getSession(eq(true))).thenReturn(session);

        when(userService.login(eq(USER_EMAIL), eq(USER_PASSWORD))).thenReturn(Optional.of(USER));

        loginCommand.processPost(request, response);

        verify(session, times(1)).setAttribute(eq("user"), eq(USER));
        verify(response, times(1)).sendRedirect(any());
    }

    @Test
    public void userShouldBeRedirectedIfLoginWasSuccessfulAndSessionIsNotNull() throws ServletException, IOException {
        when(request.getParameter(eq("email"))).thenReturn(USER_EMAIL);
        when(request.getParameter(eq("password"))).thenReturn(USER_PASSWORD);

        when(request.getSession(eq(false))).thenReturn(session);
        when(request.getSession(eq(true))).thenReturn(session);

        when(userService.login(eq(USER_EMAIL), eq(USER_PASSWORD))).thenReturn(Optional.of(USER));

        loginCommand.processPost(request, response);

        verify(session, times(1)).invalidate();
        verify(session, times(1)).setAttribute(eq("user"), eq(USER));
        verify(response, times(1)).sendRedirect(any());
    }

    @Test
    public void userShouldBeForwardedIfLoginWasUnsuccessful() throws ServletException, IOException {
        when(request.getParameter(eq("email"))).thenReturn(INVALID_USER_EMAIL);
        when(request.getParameter(eq("password"))).thenReturn(INVALID_USER_PASSWORD);
        when(request.getSession(eq(false))).thenReturn(session);
        when(request.getSession(eq(true))).thenReturn(session);
        when(userService.login(eq(INVALID_USER_EMAIL), eq(INVALID_USER_PASSWORD))).thenReturn(Optional.empty());
        when(request.getRequestDispatcher(eq(LOGIN_PAGE_PATH))).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(eq(request), eq(response));

        loginCommand.processPost(request, response);

        verify(session, times(0)).invalidate();
        verify(session, times(0)).setAttribute(anyString(), any());
        verify(response, times(0)).sendRedirect(any());
        verify(request, times(1)).setAttribute(eq("message"), anyString());
        verify(request, times(1)).getRequestDispatcher(eq(LOGIN_PAGE_PATH));
        verify(requestDispatcher, times(1)).forward(request, response);
    }
}