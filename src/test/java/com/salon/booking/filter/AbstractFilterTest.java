package com.salon.booking.filter;

import org.junit.After;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AbstractFilterTest {

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    @Mock
    protected HttpSession session;

    @Mock
    protected FilterChain chain;

    @After
    public void resetRequestMocks() {
        Mockito.reset(chain, session, request, response);
    }
}
