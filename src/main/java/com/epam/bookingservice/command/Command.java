package com.epam.bookingservice.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Command {

    private static final String MESSAGE_PARAMETER = "message";

    public abstract void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    protected void forward(String path, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    protected void forwardWithMessage(String path, String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(MESSAGE_PARAMETER, message);
        forward(path, request, response);
    }
}
