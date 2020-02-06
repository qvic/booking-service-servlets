package com.epam.bookingservice.command.timetable;

import com.epam.bookingservice.command.GetCommand;
import com.epam.bookingservice.service.TimeslotService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;

public class ShowTimetablesCommand extends GetCommand {

    private final TimeslotService timeslotService;

    public ShowTimetablesCommand(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate now = LocalDate.now();
        request.setAttribute("timetables",
                timeslotService.findAllBetween(now, now.plus(10, ChronoUnit.DAYS)));

        forward(getViewPathByName("timetables"), request, response);
    }
}
