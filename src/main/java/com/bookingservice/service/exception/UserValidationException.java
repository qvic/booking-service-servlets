package com.bookingservice.service.exception;

public class UserValidationException extends ValidationException {

    public UserValidationException(String message, String localizationKey) {
        super(message, localizationKey);
    }
}
