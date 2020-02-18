package com.bookingservice.command.user;

import com.bookingservice.command.GetAndPostCommand;
import com.bookingservice.domain.User;
import com.bookingservice.domain.UserLoginForm;
import com.bookingservice.service.exception.ValidationException;
import com.bookingservice.utility.PageUtility;
import com.bookingservice.service.AuthService;

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

        Optional<User> user;
        try {
            user = authService.login(loginForm);
        } catch (ValidationException e) {
            forwardWithMessage(LOGIN_PAGE_PATH, e.getLocalizationKey(), request, response);
            return;
        }

        if (!user.isPresent()) {
            forwardWithMessage(LOGIN_PAGE_PATH, "validation.check_credentials", request, response);
            return;
        }

        HttpSession session = createNewSession(request);
        session.setAttribute("user", user.get());

        String redirectPage = Optional.ofNullable(request.getParameter("from"))
                .orElse(ON_SUCCESS_REDIRECT);
        response.sendRedirect(redirectPage);
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