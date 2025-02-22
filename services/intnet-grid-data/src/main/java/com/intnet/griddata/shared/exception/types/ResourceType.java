package com.intnet.griddata.shared.exception.types;

public enum ResourceType {
    GRID,
    BUS,
    BUS_STATE;

    @Override
    public String toString() {
        return switch (this) {
            case GRID -> "Grid";
            case BUS -> "Bus";
            case BUS_STATE -> "Bus State";
        };
    }
}
