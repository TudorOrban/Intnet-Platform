package com.intnet.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayRouteConfig {

    private static final String TENANT_SERVICE = "http://intnet-tenant:80";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Tenant
                .route("test-route", r -> r.path("/api/v1/test/**")
                        .uri(TENANT_SERVICE))
                .build();
    }
}
