package com.intnet.griddata.shared.exception.types;

public enum ResourceType {
    GRID,
    SUBSTATION;

    @Override
    public String toString() {
        return switch (this) {
            case GRID -> "GRID";
            case SUBSTATION -> "Substation";
        };
    }
}
