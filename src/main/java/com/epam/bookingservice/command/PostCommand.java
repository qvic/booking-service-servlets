package com.epam.bookingservice.command;

import com.epam.bookingservice.command.exception.HttpMethodNotAllowedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PostCommand extends Command {

    @Override
    default void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();

        if (method.equals("POST")) {
            processPost(request, response);
        } else {
            throw new HttpMethodNotAllowedException(method);
        }
    }

    void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
