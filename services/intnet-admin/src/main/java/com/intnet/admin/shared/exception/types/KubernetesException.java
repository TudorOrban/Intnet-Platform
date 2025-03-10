package com.intnet.admin.shared.exception.types;

public class KubernetesException extends RuntimeException {
    public KubernetesException(String message) {
        super("Kubernetes error: " + message);
    }
}
