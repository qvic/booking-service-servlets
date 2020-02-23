package com.salon.booking.command.user;

import com.salon.booking.command.GetCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.salon.booking.utility.RequestUtility.getFullUrl;

public class LogoutCommand implements GetCommand {

    private static final String ON_SUCCESS_REDIRECT = "/";

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        invalidateSession(request);
        redirect(ON_SUCCESS_REDIRECT, request, response);
    }

    private void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}