package com.intnet.admin.shared.exception.types;

public class UnavailableServiceException extends RuntimeException {

    public UnavailableServiceException(IntnetServiceType serviceType) {
        super("Unavailable Hilbert Service: " + serviceType.toString());
    }
}
