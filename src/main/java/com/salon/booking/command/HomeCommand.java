package com.salon.booking.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class HomeCommand implements GetCommand {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(getViewPathByName("index"), request, response);
    }
}
