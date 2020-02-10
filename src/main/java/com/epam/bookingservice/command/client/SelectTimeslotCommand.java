package com.epam.bookingservice.command.client;

import com.epam.bookingservice.command.GetAndPostCommand;
import com.epam.bookingservice.domain.Timetable;
import com.epam.bookingservice.service.TimeslotService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;
import static com.epam.bookingservice.utility.ParseUtility.parseLocalDateOrDefault;
import static com.epam.bookingservice.utility.RequestUtility.getRequiredIntParameter;

public class SelectTimeslotCommand implements GetAndPostCommand {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/order-service";
    private static final Period DEFAULT_TIMETABLE_PERIOD = Period.ofDays(14);

    private final TimeslotService timeslotService;

    public SelectTimeslotCommand(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate from = parseLocalDateOrDefault(
                request.getParameter("from_date"),
                LocalDate.now());

        LocalDate toDefault = from.plus(DEFAULT_TIMETABLE_PERIOD);
        LocalDate to = parseLocalDateOrDefault(
                request.getParameter("to_date"),
                toDefault);

        List<Timetable> timetables = timeslotService.findAllBetween(from, to);
        request.setAttribute("timetables", timetables);
        forward(getViewPathByName("client-timetables"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int timeslotId = getRequiredIntParameter("timeslot-id", request);
        request.getSession().setAttribute("timeslotId", timeslotId);

        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }
}
