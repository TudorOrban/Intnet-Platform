package com.intnet.hostagent.shared.exception.types;

public class OperationFailedException extends RuntimeException {

    public OperationFailedException(String operation, String reason) {
        super("The " + operation + " failed. REASON: " + reason);
    }
}
