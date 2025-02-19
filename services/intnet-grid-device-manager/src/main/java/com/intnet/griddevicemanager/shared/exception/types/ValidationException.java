package com.intnet.griddevicemanager.shared.exception.types;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super("Validation error: " + message);
    }
}