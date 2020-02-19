package com.salon.booking.command;

import com.salon.booking.command.exception.HttpMethodNotAllowedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface GetCommand extends Command {

    @Override
    default void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();

        if (method.equals("GET")) {
            processGet(request, response);
        } else {
            throw new HttpMethodNotAllowedException(method);
        }
    }

    void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
