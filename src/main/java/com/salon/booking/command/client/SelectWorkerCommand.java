package com.salon.booking.command.client;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.UserService;
import com.salon.booking.utility.RequestUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class SelectWorkerCommand implements GetAndPostCommand {

    private static final long DEFAULT_WORKERS_PER_PAGE = 5;
    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/order-timeslot";

    private final UserService userService;

    public SelectWorkerCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageProperties pageProperties = getPageProperties(DEFAULT_WORKERS_PER_PAGE, request);
        Page<User> workersPage = userService.findAllWorkers(pageProperties);
        request.setAttribute("page", workersPage);

        forward(getViewPathByName("client/workers"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int workerId = RequestUtility.getRequiredIntParameter("worker-id", request);
        request.getSession().setAttribute("workerId", workerId);
        clearCart(request);

        redirect(REDIRECT_AFTER_SUBMIT, request, response);
    }

    private void clearCart(HttpServletRequest request) {
        RequestUtility.removeSessionAttribute("timeslotId", request);
    }

}
