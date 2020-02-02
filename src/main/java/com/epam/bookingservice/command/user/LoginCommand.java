package com.epam.bookingservice.command.user;

import com.epam.bookingservice.command.Command;
import com.epam.bookingservice.command.exception.HttpMethodNotAllowedException;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.utility.PageUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String method = request.getMethod();

        if (method.equals("POST")) {
            processPostRequest(request, response);
            return;
        } else if (method.equals("GET")) {
            forward(PageUtility.getViewByName("login"), request, response);
            return;
        }

        throw new HttpMethodNotAllowedException();
    }

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> user = userService.login(email, password);
        if (!user.isPresent()) {
            request.setAttribute("message", "Check credentials");
            forward(PageUtility.getViewByName("login"), request, response);
            return;
        }

        HttpSession session = createNewSession(request);
        session.setAttribute("user", user.get());

        response.sendRedirect("/app/list");
    }

    private HttpSession createNewSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return request.getSession(true);
    }
}