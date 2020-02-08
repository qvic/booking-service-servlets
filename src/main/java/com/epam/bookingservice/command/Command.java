package com.epam.bookingservice.command;

import com.epam.bookingservice.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public interface Command {

    void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    default void forward(String path, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    default void forwardWithMessage(String path, String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", message);
        forward(path, request, response);
    }

   default Optional<User> getUserFromSession(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession(false))
                .map(session -> (User) session.getAttribute("user"));
    }
}
