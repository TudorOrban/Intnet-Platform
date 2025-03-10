
export interface IntnetService {
    name: string;
    helmChartPath?: string;
    imageName?: string;
    dockerfilePath?: string;
    serviceType?: ServiceType;
    serviceBuildType?: ServiceBuildType;
    kubernetesData?: ServiceKubernetesData;
}

export interface ServiceKubernetesData {
    status: ServiceStatus;
    replicas: number;
    availableReplicas: number;
    namespace: string;
}

export enum ServiceType {
    CUSTOM = "CUSTOM",
    EXTERNAL = "EXTERNAL"
}

export enum ServiceBuildType {
    JAVA = "JAVA",
    PYTHON = "PYTHON",
    GO = "GO"
}

export enum ServiceStatus {
    RUNNING = "RUNNING",
    STOPPED = "STOPPED",
    PENDING = "PENDING",
    UNKNOWN = "UNKNOWN",
    ERROR = "ERROR"
}