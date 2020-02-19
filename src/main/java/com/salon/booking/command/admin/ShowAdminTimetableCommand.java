package com.salon.booking.command.admin;

import com.salon.booking.command.GetCommand;
import com.salon.booking.domain.Timetable;
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

    public ShowAdminTimetableCommand(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate from = parseLocalDateOrDefault(
                request.getParameter("from-date"),
                LocalDate.now());

        LocalDate toDefault = from.plus(DEFAULT_TIMETABLE_PERIOD);
        LocalDate to = parseLocalDateOrDefault(
                request.getParameter("to-date"),
                toDefault);

        List<Timetable> timetables = timeslotService.findAllBetween(from, to);
        request.setAttribute("timetables", timetables);

        forward(getViewPathByName("admin/timetables"), request, response);
    }
}
