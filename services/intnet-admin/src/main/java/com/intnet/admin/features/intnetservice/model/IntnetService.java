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
    private String imageName;
    private String dockerfilePath;
    private ServiceType serviceType;
    private ServiceBuildType serviceBuildType;

    private ServiceKubernetesData kubernetesData;
}
