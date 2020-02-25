package com.salon.booking.command.admin;

import com.salon.booking.command.GetCommand;
import com.salon.booking.domain.Timetable;
import com.salon.booking.service.TimeService;
import com.salon.booking.service.TimeslotService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static com.salon.booking.utility.ParseUtility.parseLocalDateOrDefault;

public class ShowAdminTimetableCommand implements GetCommand {

    private static final Period DEFAULT_TIMETABLE_PERIOD = Period.ofDays(14);

    private final TimeslotService timeslotService;
    private final TimeService timeService;

    public ShowAdminTimetableCommand(TimeslotService timeslotService, TimeService timeService) {
        this.timeslotService = timeslotService;
        this.timeService = timeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate currentDate = timeService.getCurrentDate();
        LocalDate from = parseLocalDateOrDefault(request.getParameter("from-date"), currentDate);
        LocalDate toDefault = from.plus(DEFAULT_TIMETABLE_PERIOD);
        LocalDate to = parseLocalDateOrDefault(request.getParameter("to-date"), toDefault);

        List<Timetable> timetables = timeslotService.findAllBetween(from, to);
        request.setAttribute("timetables", timetables);

        forward(getViewPathByName("admin/timetables"), request, response);
    }
}
