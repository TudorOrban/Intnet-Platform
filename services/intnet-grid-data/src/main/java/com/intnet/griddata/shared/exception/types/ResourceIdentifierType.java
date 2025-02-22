package com.intnet.griddata.shared.exception.types;


public enum ResourceIdentifierType {
    ID,
    USER_ID,
    EMAIL,
    NAME,
    TITLE,

    BUS_ID;

    @Override
    public String toString() {
        return switch (this) {
            case ID -> "ID";
            case USER_ID -> "User ID";
            case EMAIL -> "Email";
            case NAME -> "NAME";
            case TITLE -> "Title";

            case BUS_ID -> "Bus ID";
        };
    }
}