package com.salon.booking.command.client;

import com.salon.booking.utility.RequestUtility;
import com.salon.booking.command.GetAndPostCommand;
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
        int timeslotId = RequestUtility.getRequiredIntParameter("timeslot-id", request);
        request.getSession().setAttribute("timeslotId", timeslotId);

        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }
}
