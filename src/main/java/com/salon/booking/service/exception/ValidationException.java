package com.salon.booking.service.exception;

public class ValidationException extends RuntimeException {

    private final String localizationKey;

    public ValidationException(String message) {
        super(message);
        this.localizationKey = null;
    }

    public ValidationException(String message, String localizationKey) {
        super(message);
        this.localizationKey = localizationKey;
    }

    public String getLocalizationKey() {
        return localizationKey;
    }
}
