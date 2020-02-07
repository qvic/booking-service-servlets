package com.epam.bookingservice.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;

public class HomeCommand extends GetCommand {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(getViewPathByName("index"), request, response);
    }
}
