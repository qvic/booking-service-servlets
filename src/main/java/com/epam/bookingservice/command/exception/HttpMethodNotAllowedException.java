package com.epam.bookingservice.command.exception;

public class HttpMethodNotAllowedException extends RuntimeException {

    public HttpMethodNotAllowedException(String message) {
        super(String.format("Method '%s' is not allowed", message));
    }
}
