package com.intnet.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayRouteConfig {

    private static final String TENANT_SERVICE = "http://intnet-tenant:80";
    private static final String GRID_TOPOLOGY_SERVICE = "http://intnet-grid-topology:81";
    private static final String GRID_DATA_SERVICE = "http://intnet-grid-data:82";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Tenant
                .route("custom-roles", r -> r.path("/api/v1/custom-roles/**")
                        .uri(TENANT_SERVICE))
                .route("graphs", r -> r.path("/api/v1/graphs/**")
                        .uri(GRID_TOPOLOGY_SERVICE))
                .route("graphs", r -> r.path("/api/v1/substations/**")
                        .uri(GRID_DATA_SERVICE))
                .route("graphs", r -> r.path("/api/v1/buses/**")
                        .uri(GRID_DATA_SERVICE))
                .route("graphs", r -> r.path("/api/v1/transmission-lines/**")
                        .uri(GRID_DATA_SERVICE))
                .build();
    }
}
