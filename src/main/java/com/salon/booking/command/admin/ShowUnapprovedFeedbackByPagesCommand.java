package com.salon.booking.command.admin;

import com.salon.booking.command.GetCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class ShowUnapprovedFeedbackByPagesCommand implements GetCommand {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(getViewPathByName("feedback-list"), request, response);
    }
}
