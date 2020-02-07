package com.epam.bookingservice.command.exception;

public class InvalidRequestParameterException extends RuntimeException {

    public InvalidRequestParameterException(String message) {
        super(message);
    }
}
