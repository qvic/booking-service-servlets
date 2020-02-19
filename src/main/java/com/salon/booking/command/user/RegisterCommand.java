package com.salon.booking.command.user;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.User;
import com.salon.booking.service.exception.ValidationException;
import com.salon.booking.utility.PageUtility;
import com.salon.booking.service.AuthService;
import com.salon.booking.service.exception.UserAlreadyExistsException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements GetAndPostCommand {

    private static final String REGISTER_PAGE_PATH = PageUtility.getViewPathByName("sign-up");
    private static final String ON_SUCCESS_REDIRECT = "/app/login";

    private final AuthService authService;

    public RegisterCommand(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(REGISTER_PAGE_PATH, request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            authService.register(user);
            response.sendRedirect(ON_SUCCESS_REDIRECT);

        } catch (ValidationException e) {
            forwardWithMessage(REGISTER_PAGE_PATH, e.getLocalizationKey(), request, response);
        } catch (UserAlreadyExistsException e) {
            forwardWithMessage(REGISTER_PAGE_PATH, "validation.user_already_exists", request, response);
        }
    }
}