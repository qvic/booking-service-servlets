package com.epam.bookingservice.filter;

import com.epam.bookingservice.domain.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AuthenticationFilter implements Filter {

    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/",
            "/app",
            "/app/signup",
            "/app/login"
    );
    private static final String STATIC_RESOURCES_URL_PREFIX = "/static/";
    private static final String LOGIN_REDIRECT_URL = "/app/login";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(STATIC_RESOURCES_URL_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Optional<User> user = getUserFromSession(request);
        if (!user.isPresent() && !PUBLIC_URLS.contains(requestURI)) {
            response.sendRedirect(LOGIN_REDIRECT_URL);
        } else {
            chain.doFilter(request, response);
        }
    }

    private static Optional<User> getUserFromSession(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession(false))
                .map(session -> (User) session.getAttribute("user"));
    }
}
