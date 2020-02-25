package com.salon.booking.command.user;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.User;
import com.salon.booking.domain.UserLoginForm;
import com.salon.booking.service.AuthService;
import com.salon.booking.service.NotificationService;
import com.salon.booking.service.TimeService;
import com.salon.booking.utility.PageUtility;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
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
    private NotificationService notificationService;

    @Mock
    private AuthService authService;

    @Mock
    private TimeService timeService;

    @InjectMocks
    private LoginCommand loginCommand;

    @After
    public void resetMocks() {
        reset(authService, notificationService);
    }

    @Test
    public void userShouldBeRedirectedIfLoginWasSuccessfulAndSessionIsNull() throws ServletException, IOException {
        when(request.getParameter(eq("email"))).thenReturn(USER_EMAIL);
        when(request.getParameter(eq("password"))).thenReturn(USER_PASSWORD);
        UserLoginForm loginForm = new UserLoginForm(USER_EMAIL, USER_PASSWORD);

        when(request.getSession(eq(false))).thenReturn(null);
        when(request.getSession(eq(true))).thenReturn(session);

        when(notificationService.updateNotificationsReturningCount(any(), any())).thenReturn(2);
        when(authService.login(eq(loginForm))).thenReturn(Optional.of(USER));
        when(timeService.getCurrentDateTime()).thenReturn(LocalDateTime.now());

        loginCommand.processPost(request, response);

        verify(session).setAttribute(eq("notificationsCounter"), eq(2));
        verify(session).setAttribute(eq("user"), eq(USER));
        verify(response).sendRedirect(any());
    }

    @Test
    public void userShouldBeRedirectedIfLoginWasSuccessfulAndSessionIsNotNull() throws ServletException, IOException {
        when(request.getParameter(eq("email"))).thenReturn(USER_EMAIL);
        when(request.getParameter(eq("password"))).thenReturn(USER_PASSWORD);
        UserLoginForm loginForm = new UserLoginForm(USER_EMAIL, USER_PASSWORD);

        when(request.getSession(eq(false))).thenReturn(session);
        when(request.getSession(eq(true))).thenReturn(session);

        when(notificationService.updateNotificationsReturningCount(any(), any())).thenReturn(2);
        when(authService.login(eq(loginForm))).thenReturn(Optional.of(USER));
        when(timeService.getCurrentDateTime()).thenReturn(LocalDateTime.now());

        loginCommand.processPost(request, response);

        verify(session).setAttribute(eq("notificationsCounter"), eq(2));
        verify(session).invalidate();
        verify(session).setAttribute(eq("user"), eq(USER));
        verify(response).sendRedirect(any());
    }

    @Test
    public void userShouldBeForwardedIfLoginWasUnsuccessful() throws ServletException, IOException {
        when(request.getParameter(eq("email"))).thenReturn(INVALID_USER_EMAIL);
        when(request.getParameter(eq("password"))).thenReturn(INVALID_USER_PASSWORD);
        UserLoginForm loginForm = new UserLoginForm(INVALID_USER_EMAIL, INVALID_USER_PASSWORD);

        when(request.getSession(eq(false))).thenReturn(session);
        when(request.getSession(eq(true))).thenReturn(session);
        when(authService.login(eq(loginForm))).thenReturn(Optional.empty());
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