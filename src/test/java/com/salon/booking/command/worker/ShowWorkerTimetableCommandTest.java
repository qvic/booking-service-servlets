package com.salon.booking.command.worker;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.command.admin.ShowAdminTimetableCommand;
import com.salon.booking.command.admin.ShowUnapprovedFeedbackCommand;
import com.salon.booking.domain.Feedback;
import com.salon.booking.domain.FeedbackStatus;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.FeedbackService;
import com.salon.booking.service.TimeService;
import com.salon.booking.service.TimeslotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowWorkerTimetableCommandTest extends AbstractCommandTest {

    @Mock
    private TimeslotService timeslotService;

    @Mock
    private TimeService timeService;

    @InjectMocks
    private ShowWorkerTimetableCommand command;

    @Test
    public void processGetShouldCallTimeslotService() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("worker/timetables")))).thenReturn(requestDispatcher);

        when(request.getParameter(eq("from-date"))).thenReturn("2000-02-01");
        when(request.getParameter(eq("to-date"))).thenReturn("2000-02-10");

        when(request.getSession(eq(false))).thenReturn(session);
        User worker = User.builder().setId(1).setRole(Role.WORKER).build();
        when(session.getAttribute(eq("user"))).thenReturn(worker);

        command.processGet(request, response);

        verify(timeslotService).findAllBetweenForWorker(eq(1),
                eq(LocalDate.of(2000, 2, 1)),
                eq(LocalDate.of(2000, 2, 10)));

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void processGetShouldUseCurrentDateWhenParametersWereNotFound() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("worker/timetables")))).thenReturn(requestDispatcher);

        when(timeService.getCurrentDate()).thenReturn(LocalDate.of(2000, 2, 1));
        when(request.getParameter(eq("from-date"))).thenReturn(null);
        when(request.getParameter(eq("to-date"))).thenReturn(null);

        when(request.getSession(eq(false))).thenReturn(session);
        User worker = User.builder().setId(1).setRole(Role.WORKER).build();
        when(session.getAttribute(eq("user"))).thenReturn(worker);

        command.processGet(request, response);

        verify(timeslotService).findAllBetweenForWorker(eq(1),
                eq(LocalDate.of(2000, 2, 1)),
                eq(LocalDate.of(2000, 2, 15)));

        verify(requestDispatcher).forward(request, response);
    }
}