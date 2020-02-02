package com.epam.bookingservice.command.user;

import com.epam.bookingservice.command.Command;
import com.epam.bookingservice.utility.PageUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(PageUtility.getViewByName("index"), request, response);
    }
}