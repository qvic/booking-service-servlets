package com.epam.bookingservice.command.user;

import com.epam.bookingservice.command.GetAndPostCommand;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.service.exception.InvalidUserException;
import com.epam.bookingservice.service.exception.UserAlreadyExistsException;
import com.epam.bookingservice.utility.PageUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand extends GetAndPostCommand {

    private static final String REGISTER_PAGE_PATH = PageUtility.getViewPathByName("sign-up");
    private static final String ON_SUCCESS_REDIRECT = "/app/login";

    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(REGISTER_PAGE_PATH, request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = User.builder()
                .setEmail(email)
                .setName(name)
                .setPassword(password)
                .build();

        registerOrForwardWithMessage(user, request, response);
    }

    private void registerOrForwardWithMessage(User user, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            userService.register(user);
            response.sendRedirect(ON_SUCCESS_REDIRECT);

        } catch (InvalidUserException e) {
            forwardWithMessage(REGISTER_PAGE_PATH, "Check credentials (" + e.getReason() + ")", request, response);
        } catch (UserAlreadyExistsException e) {
            forwardWithMessage(REGISTER_PAGE_PATH, "User already exists!", request, response);
        }
    }
}