package com.salon.booking.command;

import com.salon.booking.command.exception.HttpForbiddenException;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.PageProperties;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandTest extends AbstractCommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Spy
    private Command command;

    @Test
    public void forwardWithMessage() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        command.forwardWithMessage("path", "key", request, response);

        verify(request).setAttribute(eq("message"), eq("key"));
    }

    @Test
    public void getUserWithRoleFromSessionShouldThrowExceptionWhenSessionIsNull() {
        when(request.getSession(eq(false))).thenReturn(null);

        expectedException.expect(HttpForbiddenException.class);
        command.getUserWithRoleFromSession(request, Role.CLIENT);
    }

    @Test
    public void getUserWithRoleFromSessionShouldThrowExceptionWhenSessionContainsUserWithAnotherRole() {
        when(session.getAttribute("user")).thenReturn(User.builder().setRole(Role.ADMIN).build());
        when(request.getSession(eq(false))).thenReturn(session);

        expectedException.expect(HttpForbiddenException.class);
        command.getUserWithRoleFromSession(request, Role.CLIENT);
    }

    @Test
    public void getPagePropertiesShouldReturnDefaultPageWhenNothingIsPassed() {
        PageProperties pageProperties = command.getPageProperties(12, request);

        assertEquals(new PageProperties(0, 12), pageProperties);
    }

    @Test
    public void getPagePropertiesShouldReturnDefaultPageWhenPageIsPassed() {
        when(request.getParameter(eq("page"))).thenReturn("1");

        PageProperties pageProperties = command.getPageProperties(12, request);

        assertEquals(new PageProperties(1, 12), pageProperties);
    }

    @Test
    public void getPagePropertiesShouldReturnDefaultPageWhenLimitIsPassed() {
        when(request.getParameter(eq("limit"))).thenReturn("3");

        PageProperties pageProperties = command.getPageProperties(5, request);

        assertEquals(new PageProperties(0, 3), pageProperties);
    }

    @Test
    public void getPagePropertiesShouldReturnDefaultPageWhenPageAndLimitArePassed() {
        when(request.getParameter(eq("page"))).thenReturn("2");
        when(request.getParameter(eq("limit"))).thenReturn("3");

        PageProperties pageProperties = command.getPageProperties(5, request);

        assertEquals(new PageProperties(2, 3), pageProperties);
    }
}