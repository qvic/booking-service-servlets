package com.epam.bookingservice.command.client;

import com.epam.bookingservice.command.GetAndPostCommand;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;
import static com.epam.bookingservice.utility.RequestUtility.getRequiredIntParameter;

public class SelectWorkerCommand implements GetAndPostCommand {

    private static final String REDIRECT_AFTER_SUBMIT = "/app/client/create-order";
    private final UserService userService;

    public SelectWorkerCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> workers = userService.findAllWorkers();
        request.setAttribute("workers", workers);
        forward(getViewPathByName("workers"), request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int workerId = getRequiredIntParameter("worker-id", request);
        request.getSession().setAttribute("workerId", workerId);
        response.sendRedirect(REDIRECT_AFTER_SUBMIT);
    }
}
