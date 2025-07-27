package com.pahana.edu.exception;

/**
 * Custom exception class for service layer errors.
 */
public class ServiceException extends Exception {

    // Constructor with only message
    public ServiceException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
