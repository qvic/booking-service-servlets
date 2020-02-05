package com.epam.bookingservice.service.exception;

public class EmailValidationException extends ValidationException {

    public EmailValidationException(String message) {
        super(message);
    }
}
