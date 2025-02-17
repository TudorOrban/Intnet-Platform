package com.intnet.griddata.shared.exception.types;


public enum ResourceIdentifierType {
    ID,
    USER_ID,
    EMAIL,
    NAME,
    TITLE,

    SUBSTATION_ID;

    @Override
    public String toString() {
        return switch (this) {
            case ID -> "ID";
            case USER_ID -> "User ID";
            case EMAIL -> "Email";
            case NAME -> "NAME";
            case TITLE -> "Title";

            case SUBSTATION_ID -> "Substation ID";
        };
    }
}