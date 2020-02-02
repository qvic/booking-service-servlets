package com.epam.bookingservice.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LocaleFilter implements Filter {

    private static final String LOCALE_PARAMETER = "locale";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getParameter(LOCALE_PARAMETER) != null) {
            request.getSession().setAttribute(LOCALE_PARAMETER, request.getParameter(LOCALE_PARAMETER));
        }

        chain.doFilter(request, servletResponse);
    }
}