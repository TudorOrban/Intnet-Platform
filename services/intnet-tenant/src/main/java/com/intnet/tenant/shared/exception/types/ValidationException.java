package com.intnet.tenant.shared.exception.types;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super("Validation error: " + message);
    }
}