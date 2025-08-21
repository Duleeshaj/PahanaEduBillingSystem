package com.pahana.edu.exception;

import java.io.Serial;

/** Custom exception class for service layer errors.*/
public class ServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    // Constructor with only message
    public ServiceException(String message) {
        super(message);
    }

    // Constructor with only cause
    public ServiceException(Throwable cause) {
        super(cause);
    }

    // Constructor with message and cause
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
