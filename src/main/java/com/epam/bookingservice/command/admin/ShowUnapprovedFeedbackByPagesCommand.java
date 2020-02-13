package com.epam.bookingservice.command.admin;

import com.epam.bookingservice.command.GetCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;

public class ShowUnapprovedFeedbackByPagesCommand implements GetCommand {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(getViewPathByName("feedback-list"), request, response);
    }
}
