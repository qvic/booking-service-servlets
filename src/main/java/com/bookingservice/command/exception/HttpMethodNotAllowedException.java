package com.bookingservice.command.exception;

public class HttpMethodNotAllowedException extends RuntimeException {

    public HttpMethodNotAllowedException(String method) {
        super(String.format("Method '%s' is not allowed", method));
    }
}
