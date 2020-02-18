package com.bookingservice.command.admin;

import com.bookingservice.command.PostCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeUserRoleCommand implements PostCommand {

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/app/admin/users");
    }
}
