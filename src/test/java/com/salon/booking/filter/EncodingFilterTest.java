package com.salon.booking.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EncodingFilterTest extends AbstractFilterTest {

    private EncodingFilter filter = new EncodingFilter();

    @Test
    public void doFilterShouldSetEncodingOnRequestAndResponse() throws IOException, ServletException {
        filter.doFilter(request, response, chain);

        verify(request).setCharacterEncoding(eq("UTF-8"));
        verify(response).setCharacterEncoding(eq("UTF-8"));
        verify(chain).doFilter(request, response);
    }
}