package com.salon.booking.service.exception;

public class EmailValidationException extends ValidationException {

    public EmailValidationException(String message, String localizationKey) {
        super(message, localizationKey);
    }
}
