package com.intnet.admin.features.intnetservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceKubernetesData {

    private ServiceStatus status;
    private int replicas;
    private int availableReplicas;
    private String namespace;
}
