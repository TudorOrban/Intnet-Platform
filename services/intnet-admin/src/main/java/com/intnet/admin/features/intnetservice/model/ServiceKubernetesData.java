package com.intnet.admin.features.intnetservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceKubernetesData {

    private ServiceStatus status;
    private int replicas;
    private int availableReplicas;
    private String namespace;
    private List<PodData> pods;
}
