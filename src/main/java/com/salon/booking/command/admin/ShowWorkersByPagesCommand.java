package com.salon.booking.command.admin;

import com.salon.booking.command.GetCommand;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class ShowWorkersByPagesCommand implements GetCommand {

    private static final int DEFAULT_USERS_PER_PAGE = 10;

    private final UserService userService;

    public ShowWorkersByPagesCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumber = request.getParameter("page");
        String itemsPerPage = request.getParameter("limit");
        PageProperties pageProperties = PageProperties.buildByParameters(pageNumber, itemsPerPage, DEFAULT_USERS_PER_PAGE);

        Page<User> page = userService.findAllWorkers(pageProperties);
        request.setAttribute("page", page);

        forward(getViewPathByName("admin/workers"), request, response);
    }
}