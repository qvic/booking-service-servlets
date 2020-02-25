package com.salon.booking.filter;

import com.salon.booking.command.exception.HttpForbiddenException;
import com.salon.booking.domain.User;
import com.salon.booking.utility.RequestUtility;

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
            "/app/signup",
            "/app/login",
            "/app/logout"
    );

    private static final String ADMIN_URL_PREFIX = "/app/admin/";
    private static final String WORKER_URL_PREFIX = "/app/worker/";
    private static final String CLIENT_URL_PREFIX = "/app/client/";

    private static final String STATIC_RESOURCES_URL_PREFIX = "/static/";
    private static final String LOGIN_REDIRECT_URL = "/app/login?from=%s";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isPublicResourceRequested(request.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            chainOrRedirect(request, response, chain);
        }
    }

    private static void chainOrRedirect(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<User> user = getUserFromSession(request);
        if (user.isPresent()) {
            chainIfAuthorized(user.get(), request, response, chain);
        } else {
            String fullUrl = RequestUtility.getFullUrl(String.format(LOGIN_REDIRECT_URL, request.getRequestURI()), request);
            response.sendRedirect(fullUrl);
        }
    }

    private static void chainIfAuthorized(User user, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isPermittedRequest(user, request.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            throw new HttpForbiddenException();
        }
    }

    private static boolean isPublicResourceRequested(String requestURI) {
        return requestURI.startsWith(STATIC_RESOURCES_URL_PREFIX) || PUBLIC_URLS.contains(requestURI);
    }

    private static boolean isPermittedRequest(User user, String requestURI) {
        return isClientRequestPermitted(user, requestURI) ||
                isWorkerRequestPermitted(user, requestURI) ||
                isAdminRequestPermitted(user, requestURI);
    }

    private static boolean isAdminRequestPermitted(User user, String requestURI) {
        return user.isAdmin() && requestURI.startsWith(ADMIN_URL_PREFIX);
    }

    private static boolean isWorkerRequestPermitted(User user, String requestURI) {
        return user.isWorker() && requestURI.startsWith(WORKER_URL_PREFIX);
    }

    private static boolean isClientRequestPermitted(User user, String requestURI) {
        return user.isClient() && requestURI.startsWith(CLIENT_URL_PREFIX);
    }

    private static Optional<User> getUserFromSession(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession(false))
                .map(session -> (User) session.getAttribute("user"));
    }
}
