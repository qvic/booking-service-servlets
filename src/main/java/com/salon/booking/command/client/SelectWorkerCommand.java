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

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/create-order";
    private final UserService userService;

    public SelectWorkerCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumber = request.getParameter("page");
        String itemsPerPage = request.getParameter("limit");

        PageProperties pageProperties = PageProperties.buildByParameters(pageNumber, itemsPerPage, DEFAULT_WORKERS_PER_PAGE);

        Page<User> workers = userService.findAllWorkers(pageProperties);
        request.setAttribute("page", workers);

        forward(getViewPathByName("workers"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int workerId = RequestUtility.getRequiredIntParameter("worker-id", request);
        request.getSession().setAttribute("workerId", workerId);
        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }
}
