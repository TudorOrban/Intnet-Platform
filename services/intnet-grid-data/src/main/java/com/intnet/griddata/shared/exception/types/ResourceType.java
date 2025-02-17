package com.intnet.griddata.shared.exception.types;

public enum ResourceType {
    GRID,
    SUBSTATION,
    SUBSTATION_STATE;

    @Override
    public String toString() {
        return switch (this) {
            case GRID -> "GRID";
            case SUBSTATION -> "Substation";
            case SUBSTATION_STATE -> "Substation State";
        };
    }
}
