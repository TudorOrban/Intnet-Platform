package com.intnet.admin.features.intnetservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntnetService {
    private String name;
    private String helmChartPath;
    private String dockerfilePath;

    private ServiceKubernetesData kubernetesData;
}
