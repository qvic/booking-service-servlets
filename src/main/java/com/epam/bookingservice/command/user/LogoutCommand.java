package com.epam.bookingservice.command.user;

import com.epam.bookingservice.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);
    private static final String ON_SUCCESS_REDIRECT = "/app/login";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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