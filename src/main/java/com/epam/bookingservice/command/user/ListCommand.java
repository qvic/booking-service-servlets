package com.epam.bookingservice.command.user;

import com.epam.bookingservice.command.Command;
import com.epam.bookingservice.dao.Page;
import com.epam.bookingservice.dao.PageProperties;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.utility.PageUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListCommand implements Command {

    private static final int DEFAULT_USERS_PER_PAGE = 5;

    private final UserService userService;

    public ListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumber = request.getParameter("page");
        String itemsPerPage = request.getParameter("limit");

        PageProperties pageProperties = PageProperties.fromStringParameters(pageNumber, itemsPerPage, DEFAULT_USERS_PER_PAGE);

        Page<User> page = userService.findAll(pageProperties);
        request.setAttribute("page", page);

        forward(PageUtility.getViewByName("list"), request, response);
    }
}