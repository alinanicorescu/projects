package com.voya.example.accountmanager.model.exception;

/**
 * Created by alinanicorescu on 18/12/2016.
 */
public class DataValidationException extends RuntimeException {

    public DataValidationException(String message) {
        super(message);
    }

    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidationException(Throwable cause) {
        super(cause);
    }

}
