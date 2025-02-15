package com.intnet.tenant.features.customrole.service;

import com.intnet.tenant.features.customrole.dto.CreateCustomRoleDto;
import com.intnet.tenant.features.customrole.dto.CustomRoleResponseDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CustomRoleServiceImpl implements CustomRoleService {

    private static final Logger logger = LoggerFactory.getLogger(CustomRoleServiceImpl.class);
    private final RealmResource realmResource;
    private final Keycloak keycloak;

    @Value("${keycloak.auth-server}")
    private String keycloakAuthServer;
    @Value("${keycloak.realm}")
    private String keycloakRealm;
    @Value("${keycloak.client-id}")
    private String keycloakClientId;
    @Value("${keycloak.client-secret}")
    private String keycloakClientSecret;

    public CustomRoleServiceImpl() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakAuthServer)
                .realm(keycloakRealm)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .grantType(org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
        this.realmResource = keycloak.realm(keycloakRealm);
    }

//    public CustomRoleResponseDto createCustomRole(CreateCustomRoleDto roleDto) {
//
//    }
}
