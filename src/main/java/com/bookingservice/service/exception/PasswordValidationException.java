package com.bookingservice.service.exception;

public class PasswordValidationException extends ValidationException {

    public PasswordValidationException(String message, String localizationKey) {
        super(message, localizationKey);
    }
}
