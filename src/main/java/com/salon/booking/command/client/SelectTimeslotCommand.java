package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.Timetable;
import com.salon.booking.service.TimeService;
import com.salon.booking.service.TimeslotService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static com.salon.booking.utility.RequestUtility.getFullUrl;
import static com.salon.booking.utility.RequestUtility.getIntSessionAttribute;
import static com.salon.booking.utility.RequestUtility.getRequiredIntParameter;

public class SelectTimeslotCommand implements GetAndPostCommand {

    private final TimeslotService timeslotService;
    private final TimeService timeService;

    public SelectTimeslotCommand(TimeslotService timeslotService, TimeService timeService) {
        this.timeslotService = timeslotService;
        this.timeService = timeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Integer> serviceId = getIntSessionAttribute("serviceId", request);
        Optional<Integer> workerId = getIntSessionAttribute("workerId", request);

        if (!serviceId.isPresent() || !workerId.isPresent()) {
            response.sendRedirect(getFullUrl("/app/client/order-service", request));
            return;
        }

        List<Timetable> timetables = timeslotService.findTimetablesForOrderWith(serviceId.get(), workerId.get(),
                timeService.getCurrentDate());
        request.setAttribute("timetables", timetables);

        forward(getViewPathByName("client/timetables"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int timeslotId = getRequiredIntParameter("timeslot-id", request);
        request.getSession().setAttribute("timeslotId", timeslotId);

        redirect(getFullUrl("/app/client/create-order", request), request, response);
    }
}
