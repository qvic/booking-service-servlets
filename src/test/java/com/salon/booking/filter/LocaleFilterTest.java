package com.salon.booking.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocaleFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private LocaleFilter localeFilter;

    @Test
    public void doFilterShouldSetSessionLocaleWhenRequestHasLocaleParameter() throws IOException, ServletException {
        when(request.getParameter(eq("locale"))).thenReturn("uk");
        when(request.getSession()).thenReturn(session);

        localeFilter.doFilter(request, response, chain);

        verify(session).setAttribute(eq("locale"), any());
    }
}