package com.salon.booking.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrossScriptingFilterTest extends AbstractFilterTest {

    private static final String PARAM_NAME = "param";
    private static final String TEST_XSS_STRING = "<script> eval ( \"bad things\" ) </script>";
    private static final String CLEAR_STRING = "& lt;& gt; eval & #40; \"bad things\" & #41; & lt;/& gt;";

    private CrossScriptingFilter filter = new CrossScriptingFilter();

    @Test
    public void doFilterShouldReplaceSuspiciousSymbolsInParameter() throws IOException, ServletException {
        when(request.getParameter(eq(PARAM_NAME))).thenReturn(TEST_XSS_STRING);
        doAnswer(this::assertXSSInParameterCleared).when(chain).doFilter(any(), any());

        filter.doFilter(request, response, chain);
    }

    private Void assertXSSInParameterCleared(InvocationOnMock invocation) {
        HttpServletRequest request = invocation.getArgument(0);
        assertEquals(CLEAR_STRING, request.getParameter("param"));
        return null;
    }

    @Test
    public void doFilterShouldReplaceSuspiciousSymbolsInHeaders() throws IOException, ServletException {
        when(request.getHeader(eq(PARAM_NAME))).thenReturn(TEST_XSS_STRING);
        doAnswer(this::assertXSSInHeaderCleared).when(chain).doFilter(any(), any());

        filter.doFilter(request, response, chain);
    }

    private Void assertXSSInHeaderCleared(InvocationOnMock invocation) {
        HttpServletRequest request = invocation.getArgument(0);
        assertEquals(CLEAR_STRING, request.getHeader("param"));
        return null;
    }

    @Test
    public void doFilterShouldReplaceSuspiciousSymbolsInParameters() throws IOException, ServletException {
        when(request.getParameterValues(eq(PARAM_NAME))).thenReturn(new String[]{TEST_XSS_STRING, TEST_XSS_STRING});
        doAnswer(this::assertXSSInParametersCleared).when(chain).doFilter(any(), any());

        filter.doFilter(request, response, chain);
    }

    private Void assertXSSInParametersCleared(InvocationOnMock invocation) {
        HttpServletRequest request = invocation.getArgument(0);
        assertArrayEquals(new String[]{CLEAR_STRING, CLEAR_STRING},
                request.getParameterValues("param"));
        return null;
    }
}