package com.intnet.griddevicemanager.shared.exception.types;

public enum ResourceType {
    DEVICE,
    ASSOCIATION;

    @Override
    public String toString() {
        return switch (this) {
            case DEVICE -> "Device";
            case ASSOCIATION -> "Association";
        };
    }
}
