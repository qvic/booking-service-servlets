package com.epam.bookingservice.command.user;

import com.epam.bookingservice.command.Command;
import com.epam.bookingservice.command.exception.HttpMethodNotAllowedException;
import com.epam.bookingservice.entity.Role;
import com.epam.bookingservice.entity.User;
import com.epam.bookingservice.entity.UserStatus;
import com.epam.bookingservice.service.UserService;
import com.epam.bookingservice.utility.PageUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {

    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();

        if (method.equals("POST")) {
            processPostRequest(request, response);
        } else if (method.equals("GET")) {
            forward(PageUtility.getViewByName("sign-up"), request, response);
        } else {
            throw new HttpMethodNotAllowedException();
        }
    }

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = User.builder()
                .setEmail(email)
                .setName(name)
                .setPassword(password)
                .setRole(Role.CLIENT)
                .setStatus(UserStatus.ACTIVE)
                .build();

        userService.register(user);

        forward(PageUtility.getViewByName("login"), request, response);
    }
}