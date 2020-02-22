package com.salon.booking.command.timetable;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.command.client.SelectTimeslotCommand;
import com.salon.booking.domain.Timetable;
import com.salon.booking.service.TimeslotService;
import com.salon.booking.utility.PageUtility;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectTimeslotCommandTest extends AbstractCommandTest {

    private static final String FROM_DATE_PARAM = "2020-02-01";
    private static final String TO_DATE_PARAM = "2020-02-20";
    private static final LocalDate FROM_DATE = LocalDate.of(2020, 2, 1);
    private static final LocalDate TO_DATE = LocalDate.of(2020, 2, 20);
    private static final List<Timetable> TIMETABLES = Collections.emptyList();

    private static final String TIMETABLES_PAGE_PATH = PageUtility.getViewPathByName("client-timetables");

    @Mock
    private TimeslotService timeslotService;

    @InjectMocks
    private SelectTimeslotCommand showTimetablesCommand;

    @After
    public void resetMocks() {
        Mockito.reset(timeslotService);
    }

    @Test
    public void showTimetablesShouldForwardTimetableByRequestParameters() throws ServletException, IOException {
        when(timeslotService.findAllBetween(eq(FROM_DATE), eq(TO_DATE))).thenReturn(TIMETABLES);
        when(request.getParameter("from_date")).thenReturn(FROM_DATE_PARAM);
        when(request.getParameter("to_date")).thenReturn(TO_DATE_PARAM);
        when(request.getRequestDispatcher(eq(TIMETABLES_PAGE_PATH))).thenReturn(requestDispatcher);

        showTimetablesCommand.processGet(request, response);

        verify(response).sendRedirect(anyString());
    }
}