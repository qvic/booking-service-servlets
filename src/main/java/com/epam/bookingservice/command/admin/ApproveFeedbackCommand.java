package com.epam.bookingservice.command.admin;

import com.epam.bookingservice.command.PostCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApproveFeedbackCommand implements PostCommand {

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/app/admin/feedback");
    }
}
