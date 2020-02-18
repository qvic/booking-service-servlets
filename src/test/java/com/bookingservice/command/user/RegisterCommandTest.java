package com.bookingservice.command.user;

import com.bookingservice.command.AbstractCommandTest;
import com.bookingservice.domain.User;
import com.bookingservice.service.AuthService;
import com.bookingservice.service.exception.UserValidationException;
import com.bookingservice.service.exception.UserAlreadyExistsException;
import com.bookingservice.service.exception.ValidationException;
import com.bookingservice.utility.PageUtility;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterCommandTest extends AbstractCommandTest {

    private static final String REGISTER_PAGE_PATH = PageUtility.getViewPathByName("sign-up");

    private static final String USER_EMAIL = "user_email";
    private static final String USER_NAME = "user_name";
    private static final String USER_PASSWORD = "user_password";

    private static final User USER = User.builder()
            .setEmail(USER_EMAIL)
            .setName(USER_NAME)
            .setPassword(USER_PASSWORD)
            .build();

    @Mock
    private AuthService authService;

    @InjectMocks
    private RegisterCommand registerCommand;

    @After
    public void resetMocks() {
        Mockito.reset(authService);
    }

    @Test
    public void userShouldBeRedirectedIfRegistrationWasSuccessful() throws ServletException, IOException {
        when(request.getParameter(eq("email"))).thenReturn(USER_EMAIL);
        when(request.getParameter(eq("name"))).thenReturn(USER_NAME);
        when(request.getParameter(eq("password"))).thenReturn(USER_PASSWORD);
        when(authService.register(eq(USER))).thenReturn(USER);

        registerCommand.processPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void userShouldBeForwardedWithMessageIfUserWasInvalid() throws ServletException, IOException {
        userShouldBeForwardedWithMessageIfExceptionIsThrown(ValidationException.class);
    }

    @Test
    public void userShouldBeForwardedWithMessageIfUserAlreadyExists() throws ServletException, IOException {
        userShouldBeForwardedWithMessageIfExceptionIsThrown(UserAlreadyExistsException.class);
    }

    private void userShouldBeForwardedWithMessageIfExceptionIsThrown(Class<? extends Exception> exceptionClass) throws ServletException, IOException {
        when(request.getParameter(eq("email"))).thenReturn(USER_EMAIL);
        when(request.getParameter(eq("name"))).thenReturn(USER_NAME);
        when(request.getParameter(eq("password"))).thenReturn(USER_PASSWORD);
        when(request.getRequestDispatcher(eq(REGISTER_PAGE_PATH))).thenReturn(requestDispatcher);
        when(authService.register(eq(USER))).thenThrow(exceptionClass);

        registerCommand.processPost(request, response);

        verify(request).setAttribute(eq("message"), any());
        verify(request).getRequestDispatcher(anyString());
        verify(requestDispatcher).forward(request, response);
    }
}