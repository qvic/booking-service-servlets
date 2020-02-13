package com.epam.bookingservice.service.exception;

public class InvalidUserException extends ValidationException {

    public enum Reason {
        EMPTY_PASSWORD, EMPTY_NAME, PASSWORD_TOO_SHORT, NAME_TOO_LONG, INVALID_EMAIL
    }

    private final Reason reason;

    public InvalidUserException(Reason reason) {
        super("InvalidUserException with reason " + reason);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "InvalidUserException{" +
                "reason=" + reason +
                '}';
    }
}
