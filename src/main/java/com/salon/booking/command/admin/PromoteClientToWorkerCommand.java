package com.salon.booking.command.admin;

import com.salon.booking.command.PostCommand;
import com.salon.booking.service.UserService;
import com.salon.booking.utility.RequestUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PromoteClientToWorkerCommand implements PostCommand {

    private final UserService userService;

    public PromoteClientToWorkerCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = RequestUtility.getRequiredIntParameter("client-id", request);
        userService.promoteToWorker(userId);
        
        response.sendRedirect("/app/admin/clients");
    }
}
