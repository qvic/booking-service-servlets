package com.bookingservice.command.user;

import com.bookingservice.command.GetCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements GetCommand {

    private static final String ON_SUCCESS_REDIRECT = "/";

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        invalidateSession(request);
        response.sendRedirect(ON_SUCCESS_REDIRECT);
    }

    private void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}