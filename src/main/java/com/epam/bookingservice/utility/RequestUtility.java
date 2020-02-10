package com.epam.bookingservice.utility;

import com.epam.bookingservice.command.exception.InvalidRequestParameterException;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtility {

    private RequestUtility() {

    }

    public static int getRequiredIntParameter(String name, HttpServletRequest request) {
        return ParseUtility.parseIntOrThrow(request.getParameter(name),
                () -> new InvalidRequestParameterException(name + " is a required parameter"));
    }
}
