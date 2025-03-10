package com.intnet.admin.shared.exception.types;

public enum ResourceType {
    GRID,
    BUS,
    BUS_STATE,
    GENERATOR,
    GENERATOR_STATE,
    EDGE,
    EDGE_STATE;

    @Override
    public String toString() {
        return switch (this) {
            case GRID -> "Grid";
            case BUS -> "Bus";
            case BUS_STATE -> "Bus State";
            case GENERATOR -> "Generator";
            case GENERATOR_STATE -> "Generator State";
            case EDGE -> "Edge";
            case EDGE_STATE -> "Edge State";
        };
    }
}
