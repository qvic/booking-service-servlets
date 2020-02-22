package com.salon.booking.utility;

import com.salon.booking.command.exception.InvalidRequestParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public final class RequestUtility {

    private RequestUtility() {

    }

    public static int getRequiredIntParameter(String name, HttpServletRequest request) {
        return ParseUtility.parseIntOrThrow(request.getParameter(name),
                () -> new InvalidRequestParameterException(name + " is a required parameter"));
    }

    public static String getRequiredStringParameter(String name, HttpServletRequest request) {
        String parameter = request.getParameter(name);
        if (parameter == null) {
            throw new InvalidRequestParameterException(name + " is a required parameter");
        }

        return parameter;
    }

    private static <T> Optional<T> getSessionAttribute(String name, HttpServletRequest request, Class<T> tClass) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Optional.empty();
        }

        T attribute = tClass.cast(session.getAttribute(name));

        return Optional.ofNullable(attribute);
    }

    public static Optional<Integer> getIntSessionAttribute(String name, HttpServletRequest request) {
        return getSessionAttribute(name, request, Integer.class);
    }

    public static void removeSessionAttribute(String name, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(name);
        }
    }
}
