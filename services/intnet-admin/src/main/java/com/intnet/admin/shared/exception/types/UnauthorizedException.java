package com.intnet.admin.shared.exception.types;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super("Unauthorized to request this resource: " + message);
    }
}
