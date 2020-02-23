package com.salon.booking.command;

import com.salon.booking.command.exception.HttpForbiddenException;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.utility.RequestUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public interface Command {

    void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    default void forward(String path, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    default void forwardWithMessage(String path, String localizationKey, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", localizationKey);
        forward(path, request, response);
    }

    default User getUserWithRoleFromSession(HttpServletRequest request, Role role) {
        return Optional.ofNullable(request.getSession(false))
                .map(session -> (User) session.getAttribute("user"))
                .filter(user -> user.getRole() == role)
                .orElseThrow(HttpForbiddenException::new);
    }

    default PageProperties getPageProperties(long defaultLimit, HttpServletRequest request) {
        String pageNumber = request.getParameter("page");
        String itemsPerPage = request.getParameter("limit");

        return PageProperties.buildByParameters(pageNumber, itemsPerPage, defaultLimit);
    }

    default void redirect(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(RequestUtility.getFullUrl(url, request));
    }
}
