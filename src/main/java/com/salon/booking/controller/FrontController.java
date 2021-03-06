package com.salon.booking.controller;

import com.salon.booking.command.Command;
import com.salon.booking.context.ApplicationInjector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class FrontController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    private final Map<String, Command> commands;

    public FrontController() {
        ApplicationInjector injector = ApplicationInjector.getInstance();

        commands = injector.getCommands();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        LOGGER.debug(requestURI);

        Command command = commands.get(requestURI);
        if (command == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        command.execute(request, response);
    }
}
