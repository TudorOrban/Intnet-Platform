package com.intnet.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayRouteConfig {

    private static final String TENANT_SERVICE = "http://std-release-intnet-tenant:80";
    private static final String GRID_TOPOLOGY_SERVICE = "http://std-release-intnet-grid-topology:81";
    private static final String GRID_DATA_SERVICE = "http://std-release-intnet-grid-data:82";
    private static final String GRID_HISTORY_SERVICE = "http://std-release-intnet-grid-history:83";
    private static final String GRID_DEVICE_MANAGER_SERVICE = "http://std-release-intnet-grid-device-manager:85";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Tenant
                .route("custom-roles", r -> r.path("/api/v1/custom-roles/**")
                        .uri(TENANT_SERVICE))
                .route("graphs", r -> r.path("/api/v1/graphs/**")
                        .uri(GRID_TOPOLOGY_SERVICE))
                .route("substations", r -> r.path("/api/v1/substations/**")
                        .uri(GRID_DATA_SERVICE))
                .route("buses", r -> r.path("/api/v1/buses/**")
                        .uri(GRID_DATA_SERVICE))
                .route("transmission-lines", r -> r.path("/api/v1/transmission-lines/**")
                        .uri(GRID_DATA_SERVICE))
                .route("bus-state-records", r -> r.path("/api/v1/bus-state-records/**")
                        .uri(GRID_HISTORY_SERVICE))
                .route("devices", r -> r.path("/api/v1/devices/**")
                        .uri(GRID_DEVICE_MANAGER_SERVICE))
                .route("grid-element-associations", r -> r.path("/api/v1/grid-element-associations/**")
                        .uri(GRID_DEVICE_MANAGER_SERVICE))
                .build();
    }
}
