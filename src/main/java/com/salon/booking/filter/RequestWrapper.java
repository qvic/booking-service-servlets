package com.salon.booking.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;

public final class RequestWrapper extends HttpServletRequestWrapper {

    RequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        return Arrays.stream(values)
                .map(this::clearXSS)
                .toArray(String[]::new);
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }

        return clearXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }

        return clearXSS(value);
    }

    private String clearXSS(String value) {
        return value.replace("<", "& lt;")
                .replace(">", "& gt;")
                .replace("(", "& #40;")
                .replace(")", "& #41;")
                .replace("'", "& #39;")
                .replaceAll("eval\\s*\\(.*\\)", "")
                .replaceAll("[\"'][\\s]*javascript:.*[\"']", "\"\"")
                .replace("script", "");
    }
}