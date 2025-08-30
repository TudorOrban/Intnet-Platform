package com.intnet.tenant.features.customrole.service;

import com.intnet.tenant.features.customrole.dto.CreateCustomRoleDto;
import com.intnet.tenant.features.customrole.dto.CustomRoleResponseDto;
import com.intnet.tenant.features.policy.model.Policy;
import com.intnet.tenant.shared.exception.types.*;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/*
 * Service responsible for CRUD ops for the CustomRole feature
 */
@Service
public class CustomRoleServiceImpl implements CustomRoleService {

    private static final Logger logger = LoggerFactory.getLogger(CustomRoleServiceImpl.class);
    private final RealmResource realmResource;
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    public CustomRoleServiceImpl(
            @Value("${keycloak.auth-server}") String keycloakAuthServer,
            @Value("${keycloak.realm}") String keycloakRealm,
            @Value("${keycloak.client-id}") String keycloakClientId,
            @Value("${keycloak.client-secret}") String keycloakClientSecret) {

        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakAuthServer)
                .realm(keycloakRealm)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .grantType(org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS)
                .build();

        this.realmResource = keycloak.realm(keycloakRealm);
    }

    @PreDestroy
    public void close() {
        keycloak.close();
    }

    public CustomRoleResponseDto createCustomRole(CreateCustomRoleDto roleDto) {
        String roleName = roleDto.getName();
        String description = roleDto.getDescription();
        List<String> policyNames = roleDto.getPolicyNames();

        try {
            try {
                realmResource.roles().get(roleName).toRepresentation();
                throw new ResourceAlreadyExistsException(roleName, ResourceType.ROLE, ResourceIdentifierType.NAME);
            } catch (NotFoundException e) {
                RoleRepresentation compositeRole = new RoleRepresentation();
                compositeRole.setName(roleName);
                compositeRole.setDescription(description);
                compositeRole.setComposite(true);

                List<RoleRepresentation> policyRepresentations = new ArrayList<>();
                for (String policyName : policyNames) {
                    try {
                        RoleRepresentation policy = realmResource.roles().get(policyName).toRepresentation();
                        policyRepresentations.add(policy);
                    } catch (NotFoundException ex) {
                        throw new ResourceNotFoundException(policyName, ResourceType.POLICY, ResourceIdentifierType.NAME);
                    }
                }

                RoleRepresentation.Composites composites = new RoleRepresentation.Composites();
                Map<String, Set<String>> realmRoles = new HashMap<>(); // Realm -> Roles
                realmRoles.put(keycloakRealm, policyRepresentations.stream().map(RoleRepresentation::getName).collect(Collectors.toSet()));
                composites.setRealm(realmRoles.get(keycloakRealm));
                compositeRole.setComposites(composites);

                realmResource.roles().create(compositeRole);

                CustomRoleResponseDto responseDto = new CustomRoleResponseDto();
                responseDto.setName(roleName);
                responseDto.setDescription(description);
                responseDto.setPolicies(policyRepresentations.stream().map(policy -> new Policy(policy.getName(), policy.getDescription())).toList());
                return responseDto;
            }
        } catch (Exception e) {
            logger.error("Error creating custom role: ", e);
            throw new OperationFailedException("Custom Role creation", e.getMessage());
        }
    }
}
