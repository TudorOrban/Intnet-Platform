package com.intnet.hostagent.shared.exception.types;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String identifier, ResourceType resourceType, ResourceIdentifierType identifierType) {
        super("There already exists a " + resourceType.toString() +
                " with " + identifierType.toString() + ": " + identifier);
    }
}