package com.salon.booking.filter;

import com.salon.booking.command.exception.HttpForbiddenException;
import com.salon.booking.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);

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

        String requestURI = request.getRequestURI();
        Optional<User> user = getUserFromSession(request);

        if (isPublicResourceRequested(requestURI)) {
            chain.doFilter(request, response);
        } else {
            if (user.isPresent()) {
                if (isPermittedRequest(user.get(), requestURI)) {
                    chain.doFilter(request, response);
                } else {
                    throw new HttpForbiddenException();
                }
            } else {
                response.sendRedirect(String.format(LOGIN_REDIRECT_URL, request.getRequestURI()));
            }
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
