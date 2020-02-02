package com.epam.bookingservice.controller;

import com.epam.bookingservice.command.Command;
import com.epam.bookingservice.context.ApplicationInjector;
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

    // записати в базу кирилицю з джави і дістати
    // покрити юніт тестами
    // button change language, pagination
    // few jsp: login, registration, show table
    // resource bundle messages
    // javadoc for passwordencryptor?
    // test dao
    // login
    // register
    // localization (/link?language=en&page=1)
    // show table of users, paged
    // validate page number

    private final Map<String, Command> commands;
    private final Command defaultCommand;

    public FrontController() {
        ApplicationInjector injector = ApplicationInjector.getInstance();

        commands = injector.getCommands();
        defaultCommand = injector.getDefaultCommand();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        LOGGER.info("Request URI: " + requestURI);

        Command command = commands.getOrDefault(requestURI, defaultCommand);
        command.execute(request, response);
    }
}
