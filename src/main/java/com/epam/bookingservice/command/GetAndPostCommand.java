package com.epam.bookingservice.command;

import com.epam.bookingservice.command.exception.HttpMethodNotAllowedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class GetAndPostCommand extends GetCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();

        if (method.equals("POST")) {
            processPost(request, response);
        } else if (method.equals("GET")) {
            processGet(request, response);
        } else {
            throw new HttpMethodNotAllowedException(method);
        }
    }

    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
