package com.intnet.tenant.shared.exception.types;

public enum ResourceType {
    POLICY,
    ROLE;

    @Override
    public String toString() {
        return switch (this) {
            case POLICY -> "Policy";
            case ROLE -> "Role";
        };
    }
}
