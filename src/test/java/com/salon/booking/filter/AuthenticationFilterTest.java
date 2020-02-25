package com.salon.booking.filter;

import com.salon.booking.command.exception.HttpForbiddenException;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest extends AbstractFilterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private AuthenticationFilter filter = new AuthenticationFilter();

    @Test
    public void doFilterShouldCallChainDoFilterWhenIndexIsAccessedByUnauthenticatedUser() throws IOException, ServletException {
        doFilterShouldCallChainDoFilterWhenUrlIsAccessedByUnauthenticatedUser("/");
    }

    @Test
    public void doFilterShouldCallChainDoFilterWhenLoginIsAccessedByUnauthenticatedUser() throws IOException, ServletException {
        doFilterShouldCallChainDoFilterWhenUrlIsAccessedByUnauthenticatedUser("/app/login");
    }

    @Test
    public void doFilterShouldCallChainDoFilterWhenLogoutIsAccessedByUnauthenticatedUser() throws IOException, ServletException {
        doFilterShouldCallChainDoFilterWhenUrlIsAccessedByUnauthenticatedUser("/app/login");
    }

    @Test
    public void doFilterShouldCallChainDoFilterWhenSignUpIsAccessedByUnauthenticatedUser() throws IOException, ServletException {
        doFilterShouldCallChainDoFilterWhenUrlIsAccessedByUnauthenticatedUser("/app/signup");
    }

    @Test
    public void doFilterShouldCallRedirectWhenClientPageIsAccessedByUnauthenticatedUser() throws IOException, ServletException {
        doFilterShouldRedirectToLoginWhenUrlIsAccessedByUnauthenticatedUser("/app/client/feedback");
    }

    @Test
    public void doFilterShouldCallRedirectWhenClientPageIsAccessedByClient() throws IOException, ServletException {
        doFilterShouldCallChainDoFilterWhenUrlIsAccessedByAuthenticatedUser("/app/client/feedback", Role.CLIENT);
    }

    @Test
    public void doFilterShouldThrowExceptionWhenAdminPageIsAccessedByClient() throws IOException, ServletException {
        doFilterShouldThrowExceptionWhenUrlIsForbiddenToAuthenticatedUser("/app/admin/feedback", Role.CLIENT);
    }

    private void doFilterShouldCallChainDoFilterWhenUrlIsAccessedByUnauthenticatedUser(String url) throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(url);
        when(request.getSession(eq(false))).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verifyZeroInteractions(response);
    }

    private void doFilterShouldRedirectToLoginWhenUrlIsAccessedByUnauthenticatedUser(String url) throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(url);
        when(session.getAttribute(eq("user"))).thenReturn(null);
        when(request.getSession(eq(false))).thenReturn(session);
        when(request.getContextPath()).thenReturn("");

        filter.doFilter(request, response, chain);

        verify(response).sendRedirect(eq("/app/login?from=" + url));
        verifyZeroInteractions(chain);
    }

    private void doFilterShouldCallChainDoFilterWhenUrlIsAccessedByAuthenticatedUser(String url, Role role) throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(url);
        when(session.getAttribute(eq("user"))).thenReturn(User.builder().setRole(role).build());
        when(request.getSession(eq(false))).thenReturn(session);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verifyZeroInteractions(response);
    }

    private void doFilterShouldThrowExceptionWhenUrlIsForbiddenToAuthenticatedUser(String url, Role role) throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(url);
        when(session.getAttribute(eq("user"))).thenReturn(User.builder().setRole(role).build());
        when(request.getSession(eq(false))).thenReturn(session);

        expectedException.expect(HttpForbiddenException.class);
        filter.doFilter(request, response, chain);
    }
}