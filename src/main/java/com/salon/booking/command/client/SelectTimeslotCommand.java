package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.Timetable;
import com.salon.booking.service.TimeslotService;
import com.salon.booking.utility.RequestUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class SelectTimeslotCommand implements GetAndPostCommand {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/create-order";

    private final TimeslotService timeslotService;

    public SelectTimeslotCommand(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Integer> serviceId = RequestUtility.getIntSessionAttribute("serviceId", request);
        Optional<Integer> workerId = RequestUtility.getIntSessionAttribute("workerId", request);

        if (!serviceId.isPresent() || !workerId.isPresent()) {
            response.sendRedirect("/app/client/order-service");
            return;
        }

        List<Timetable> timetables = timeslotService.findTimetablesForServiceWithWorker(serviceId.get(), workerId.get());
        request.setAttribute("timetables", timetables);

        forward(getViewPathByName("client/timetables"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int timeslotId = RequestUtility.getRequiredIntParameter("timeslot-id", request);
        request.getSession().setAttribute("timeslotId", timeslotId);

        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }
}
