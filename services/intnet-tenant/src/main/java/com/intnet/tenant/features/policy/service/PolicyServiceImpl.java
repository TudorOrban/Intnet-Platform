package com.intnet.tenant.features.policy.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intnet.tenant.features.policy.model.Policy;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class PolicyServiceImpl implements PolicyService {

    private static final Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);

    @Value("${keycloak.auth-server}")
    private String keycloakAuthServer;
    @Value("${keycloak.realm}")
    private String keycloakRealm;
    @Value("${keycloak.client-id}")
    private String keycloakClientId;
    @Value("${keycloak.client-secret}")
    private String keycloakClientSecret;

    @Value("classpath:policies/policies.json")
    private Resource policiesResource;

    @EventListener(ApplicationReadyEvent.class)
    public void createKeycloakRoles() {
        try {
            String policiesJson = FileCopyUtils.copyToString(new InputStreamReader(policiesResource.getInputStream(), StandardCharsets.UTF_8));

            ObjectMapper mapper = new ObjectMapper();
            List<Policy> policies = mapper.readValue(policiesJson, new TypeReference<>() {});

            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakAuthServer)
                    .realm(keycloakRealm)
                    .clientId(keycloakClientId)
                    .clientSecret(keycloakClientSecret)
                    .grantType(org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();

            RealmResource realmResource = keycloak.realm(keycloakRealm);

            for (Policy policy : policies) {
                this.createPolicyIfAbsent(realmResource, policy);
            }

            keycloak.close();
        } catch (Exception e) {
            logger.error("Error creating/updating Keycloak roles: ", e);
        }
    }

    private void createPolicyIfAbsent(RealmResource realmResource, Policy policy) {
        String roleName = policy.getName();
        String description = policy.getDescription();

        try {
            realmResource.roles().get(roleName).toRepresentation();
            logger.info("Role {} already exists. Skipping creation.", roleName);
        } catch (NotFoundException e) {
            RoleRepresentation roleRepresentation = new RoleRepresentation();
            roleRepresentation.setName(roleName);
            roleRepresentation.setDescription(description);

            Map<String, List<String>> attributes = new HashMap<>();
            attributes.put("role_type", new ArrayList<>(Collections.singleton("POLICY")));
            roleRepresentation.setAttributes(attributes);

            realmResource.roles().create(roleRepresentation);
            logger.info("Role {} created successfully.", roleName);
        } catch (Exception innerException) {
            logger.error("Error processing policy {}: {}", policy.getName(), innerException.getMessage());
        }
    }
}
