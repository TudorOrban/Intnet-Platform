package com.intnet.admin.features.intnetservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceKubernetesData {

    private String status;
    private int replicas;
    private String namespace;
}
