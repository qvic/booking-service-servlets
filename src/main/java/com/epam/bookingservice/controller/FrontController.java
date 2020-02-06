package com.epam.bookingservice.controller;

import com.epam.bookingservice.command.Command;
import com.epam.bookingservice.context.ApplicationInjector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class FrontController extends HttpServlet {

    private final Map<String, Command> commands;

    public FrontController() {
        ApplicationInjector injector = ApplicationInjector.getInstance();

        commands = injector.getCommands();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        Command command = commands.get(requestURI);
        if (command == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        command.execute(request, response);
    }
}
