package com.salon.booking.command;

import org.junit.After;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class AbstractCommandTest {

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    @Mock
    protected RequestDispatcher requestDispatcher;

    @Mock
    protected HttpSession session;

    @After
    public void resetRequestMocks() {
        Mockito.reset(requestDispatcher, session, request, response);
    }
}
