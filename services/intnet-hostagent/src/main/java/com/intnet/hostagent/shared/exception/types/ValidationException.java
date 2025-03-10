package com.intnet.hostagent.shared.exception.types;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super("Validation error: " + message);
    }
}