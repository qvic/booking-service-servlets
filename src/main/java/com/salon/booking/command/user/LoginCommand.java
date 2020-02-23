package com.salon.booking.command.user;

import com.salon.booking.command.GetAndPostCommand;
import com.salon.booking.domain.User;
import com.salon.booking.domain.UserLoginForm;
import com.salon.booking.service.AuthService;
import com.salon.booking.service.exception.ValidationException;
import com.salon.booking.utility.PageUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements GetAndPostCommand {

    private static final String LOGIN_PAGE_PATH = PageUtility.getViewPathByName("login");
    private static final String ON_SUCCESS_REDIRECT = "/";

    private final AuthService authService;

    public LoginCommand(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserLoginForm loginForm = new UserLoginForm(
                request.getParameter("email"),
                request.getParameter("password"));

        Optional<User> loggedInUser;
        try {
            loggedInUser = authService.login(loginForm);
        } catch (ValidationException e) {
            forwardWithMessage(LOGIN_PAGE_PATH, e.getLocalizationKey(), request, response);
            return;
        }

        if (!loggedInUser.isPresent()) {
            forwardWithMessage(LOGIN_PAGE_PATH, "validation.check_credentials", request, response);
            return;
        }

        HttpSession session = createNewSession(request);
        session.setAttribute("user", loggedInUser.get());

        String redirectPage = Optional.ofNullable(request.getParameter("from"))
                .orElse(ON_SUCCESS_REDIRECT);
        redirect(redirectPage, request, response);
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(LOGIN_PAGE_PATH, request, response);
    }

    private HttpSession createNewSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return request.getSession(true);
    }
}