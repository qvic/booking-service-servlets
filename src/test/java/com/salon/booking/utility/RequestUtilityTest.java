package com.salon.booking.utility;

import com.salon.booking.command.exception.InvalidRequestParameterException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestUtilityTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private HttpServletRequest request;

    @Test
    public void getRequiredIntParameterShouldRetrieveAndParseIntParameterCorrectly() {
        String parameterName = "testname";
        when(request.getParameter(eq(parameterName))).thenReturn("123");

        int intValue = RequestUtility.getRequiredIntParameter(parameterName, request);
        assertEquals(123, intValue);
    }

    @Test
    public void getRequiredIntParameterShouldThrowWhenParameterIsInvalid() {
        String parameterName = "testname";
        when(request.getParameter(eq(parameterName))).thenReturn("abc");

        expectedException.expect(InvalidRequestParameterException.class);
        RequestUtility.getRequiredIntParameter(parameterName, request);
    }
}