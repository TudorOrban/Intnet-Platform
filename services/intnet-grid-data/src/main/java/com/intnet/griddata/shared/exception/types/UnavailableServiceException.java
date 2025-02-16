package com.intnet.griddata.shared.exception.types;

public class UnavailableServiceException extends RuntimeException {

    public UnavailableServiceException(IntnetServiceType serviceType) {
        super("Unavailable Hilbert Service: " + serviceType.toString());
    }
}
