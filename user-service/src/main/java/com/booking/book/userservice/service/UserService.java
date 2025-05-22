package com.booking.book.userservice.service;

import com.booking.book.userservice.dto.UserDto;
import com.booking.book.userservice.dto.UserResponseDto;
import com.booking.book.userservice.mapper.UserMapper;
import com.booking.book.userservice.util.KeycloakAdapter;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service

public class UserService {

    private final UserMapper userMapper;

    private final KeycloakAdapter keycloakAdapter;

    @Autowired
    public UserService(
            KeycloakAdapter keycloakAdapter,
            UserMapper userMapper
    ) {
        this.keycloakAdapter = keycloakAdapter;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponseDto addUser(UserDto newUserRecord) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUserRecord.name());
        userRepresentation.setLastName(newUserRecord.surname());
        userRepresentation.setUsername(newUserRecord.username());
        userRepresentation.setEmail(newUserRecord.email());
        //TODO: set up email verification
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(newUserRecord.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));
        UsersResource usersResource = keycloakAdapter.getUsersResource();
        Response response = usersResource.create(userRepresentation);
        if (!Objects.equals(201, response.getStatus())) {

            throw new RuntimeException("Status code " + response.getStatus());
        }

        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(newUserRecord.username(), true);
        UserRepresentation userRepresentationAfterSave = userRepresentations.get(0);

        assignRole(userRepresentationAfterSave.getId(), "USER");

        return userMapper.toUserDto(userRepresentationAfterSave);
    }

    /**
     * Method for role assignment to user
     *
     * @param userId   user's unique identifier
     * @param roleName role's name to assign to user
     * @author FanTamas
     */
    private void assignRole(String userId, String roleName) {

        UserResource user = keycloakAdapter.getUsersResource().get(userId);
        RolesResource rolesResource = keycloakAdapter.getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(representation));
    }
}
