package com.epam.bookingservice.command.user;

import com.epam.bookingservice.command.GetAndPostCommand;
import com.epam.bookingservice.domain.User;
import com.epam.bookingservice.entity.UserEntity;
import com.epam.bookingservice.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.epam.bookingservice.utility.PageUtility.getViewPathByName;

public class LoginCommand extends GetAndPostCommand {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    private static final String LOGIN_PAGE_PATH = getViewPathByName("login");
    public static final String ON_SUCCESS_REDIRECT = "/app/users";

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> user = userService.login(email, password);

        if (!user.isPresent()) {
            forwardWithMessage(getViewPathByName("login"),
                    "Check credentials", request, response);
            return;
        }

        HttpSession session = createNewSession(request);
        session.setAttribute("user", user.get());

        response.sendRedirect(ON_SUCCESS_REDIRECT);
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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