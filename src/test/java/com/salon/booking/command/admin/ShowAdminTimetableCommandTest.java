package com.salon.booking.command.admin;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.command.exception.InvalidRequestParameterException;
import com.salon.booking.service.TimeService;
import com.salon.booking.service.TimeslotService;
import com.salon.booking.service.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowAdminTimetableCommandTest extends AbstractCommandTest {

    @Mock
    private TimeslotService timeslotService;

    @Mock
    private TimeService timeService;

    @InjectMocks
    private ShowAdminTimetableCommand command;

    @Test
    public void processGetShouldCallTimeslotService() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("admin/timetables")))).thenReturn(requestDispatcher);

        when(request.getParameter(eq("from-date"))).thenReturn("2000-02-01");
        when(request.getParameter(eq("to-date"))).thenReturn("2000-02-10");

        command.processGet(request, response);

        verify(timeslotService).findAllBetween(
                eq(LocalDate.of(2000, 2, 1)),
                eq(LocalDate.of(2000, 2, 10)));

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void processGetShouldUseCurrentDateWhenFromParameterWasNotFound() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("admin/timetables")))).thenReturn(requestDispatcher);

        when(timeService.getCurrentDate()).thenReturn(LocalDate.of(2000, 2, 1));
        when(request.getParameter(eq("from-date"))).thenReturn(null);
        when(request.getParameter(eq("to-date"))).thenReturn("2000-02-10");

        command.processGet(request, response);

        verify(timeslotService).findAllBetween(
                eq(LocalDate.of(2000, 2, 1)),
                eq(LocalDate.of(2000, 2, 10)));

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void processGetShouldUseDefaultPeriodWhenToParameterWasNotFound() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("admin/timetables")))).thenReturn(requestDispatcher);

        when(request.getParameter(eq("from-date"))).thenReturn("2000-02-01");
        when(request.getParameter(eq("to-date"))).thenReturn(null);

        command.processGet(request, response);

        verify(timeslotService).findAllBetween(
                eq(LocalDate.of(2000, 2, 1)),
                eq(LocalDate.of(2000, 2, 15)));

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void processGetShouldUseCurrentDateWithDefaultPeriodWhenParametersWereNotFound() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("admin/timetables")))).thenReturn(requestDispatcher);

        when(timeService.getCurrentDate()).thenReturn(LocalDate.of(2000, 2, 1));
        when(request.getParameter(eq("from-date"))).thenReturn(null);
        when(request.getParameter(eq("to-date"))).thenReturn(null);

        command.processGet(request, response);

        verify(timeslotService).findAllBetween(
                eq(LocalDate.of(2000, 2, 1)),
                eq(LocalDate.of(2000, 2, 15)));

        verify(requestDispatcher).forward(request, response);
    }
}