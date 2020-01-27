package com.epam.app.dao.exception;


public class DatabaseRuntimeException extends RuntimeException {

    public DatabaseRuntimeException() {
    }

    public DatabaseRuntimeException(String message) {
        super(message);
    }

    public DatabaseRuntimeException(String message, Exception cause) {
        super(message, cause);
    }
}