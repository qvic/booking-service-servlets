package com.epam.bookingservice.command.admin;

import com.epam.bookingservice.command.GetCommand;
import com.epam.bookingservice.domain.page.Page;
import com.epam.bookingservice.domain.page.PageProperties;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;

public class ShowUsersByPagesCommand implements GetCommand {

    private static final int DEFAULT_USERS_PER_PAGE = 5;

    private final UserService userService;

    public ShowUsersByPagesCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumber = request.getParameter("page");
        String itemsPerPage = request.getParameter("limit");

        PageProperties pageProperties = PageProperties.buildByParameters(pageNumber, itemsPerPage, DEFAULT_USERS_PER_PAGE);

        Page<User> page = userService.findAll(pageProperties);
        request.setAttribute("page", page);

        forward(getViewPathByName("users"), request, response);
    }
}