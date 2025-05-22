package com.booking.book.userservice.mapper;

import com.booking.book.userservice.dto.UserResponseDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author FanTamas
 * @version 0.0.1
 */
@Service
public class UserMapper {

    /**
     * Method for transferring data from received user DAO from identity provider
     * to newly created user DTO
     *
     * @param user persisted user info
     * @return persisted user's info in DTO format
     * @author FanTamas
     */
    public UserResponseDto toUserDto(UserRepresentation user) {

        return new UserResponseDto(
                user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getUsername(),
                user.getEmail()
        );
    }

    /**
     * Method for transferring data from JWT claims to newly created user DTO
     *
     * @param tokenClaims given JWT claims
     * @return persisted user data
     * @author FanTamas
     */
    public UserResponseDto toUserDto(Map<String, Object> tokenClaims) {

        return new UserResponseDto(
                (String) tokenClaims.get("sub"),
                (String) tokenClaims.get("family_name"),
                (String) tokenClaims.get("given_name"),
                (String) tokenClaims.get("preferred_username"),
                (String) tokenClaims.get("email")
        );
    }
}
