package com.bookingservice.utility;

import com.bookingservice.command.exception.InvalidRequestParameterException;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtility {

    private RequestUtility() {

    }

    public static int getRequiredIntParameter(String name, HttpServletRequest request) {
        return ParseUtility.parseIntOrThrow(request.getParameter(name),
                () -> new InvalidRequestParameterException(name + " is a required parameter"));
    }
}
