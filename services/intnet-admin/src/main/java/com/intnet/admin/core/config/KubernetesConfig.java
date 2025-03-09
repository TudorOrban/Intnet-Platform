package com.intnet.admin.core.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class KubernetesConfig {

    @Bean
    public ApiClient apiClient() throws IOException {
        return Config.defaultClient();
    }

    @Bean
    public CoreV1Api coreV1Api(ApiClient apiClient) {
        return new CoreV1Api(apiClient);
    }

    @Bean
    public AppsV1Api appsV1Api(ApiClient apiClient) {
        return new AppsV1Api(apiClient);
    }
}
