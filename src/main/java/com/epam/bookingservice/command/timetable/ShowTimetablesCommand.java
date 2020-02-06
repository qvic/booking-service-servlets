package com.epam.bookingservice.command.timetable;

import com.epam.bookingservice.command.GetCommand;
import com.epam.bookingservice.domain.Timetable;
import com.epam.bookingservice.service.TimeslotService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;
import static com.epam.bookingservice.utility.ParseUtility.parseLocalDateOrDefault;

public class ShowTimetablesCommand extends GetCommand {

    private static final int DEFAULT_TIMETABLE_PERIOD = 14;

    private final TimeslotService timeslotService;

    public ShowTimetablesCommand(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate from = parseLocalDateOrDefault(request.getParameter("from_date"),
                LocalDate.now());
        LocalDate to = parseLocalDateOrDefault(request.getParameter("to_date"),
                LocalDate.now().plus(DEFAULT_TIMETABLE_PERIOD, ChronoUnit.DAYS));

        List<Timetable> timetables = timeslotService.findAllBetween(from, to);
        request.setAttribute("timetables", timetables);
        forward(getViewPathByName("timetables"), request, response);
    }
}
