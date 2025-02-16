package com.intnet.griddata.shared.exception.types;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String identifier, ResourceType resourceType, ResourceIdentifierType identifierType) {
        super("The " + resourceType.toString() +
                " with " + identifierType.toString() + ": " + identifier +
                " was not found.");
    }
}
