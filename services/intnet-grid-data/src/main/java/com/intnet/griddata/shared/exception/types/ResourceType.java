package com.intnet.griddata.shared.exception.types;

public enum ResourceType {
    GRID,
    SUBSTATION,
    SUBSTATION_STATE,
    BUS,
    BUS_STATE;

    @Override
    public String toString() {
        return switch (this) {
            case GRID -> "GRID";
            case SUBSTATION -> "Substation";
            case SUBSTATION_STATE -> "Substation State";
            case BUS -> "Bus";
            case BUS_STATE -> "Bus State";
        };
    }
}
