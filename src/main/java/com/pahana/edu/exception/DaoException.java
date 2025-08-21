package com.pahana.edu.exception;

/**
 * Custom exception class for DAO layer errors.
 */
public class DaoException extends Exception {

    // Constructor with only message
    public DaoException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
