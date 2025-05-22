package com.booking.book.userservice.controller;

import com.booking.book.userservice.dto.UserDto;
import com.booking.book.userservice.dto.UserResponseDto;
import com.booking.book.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author FanTamas
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint for user record creation in DB (basically user registration).
     *
     * @param userDto requested data in DTO format to store
     * @return saved user data in DTO format with 201 status code
     * @author FanTamas
     */
    @Operation(
            description = "Endpoint for user record creation in DB (basically user registration).",
            summary = "Store new user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data that is requested for storing",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UserDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n\"username\": \"demoUser\",\n" +
                                                            "\"surname\": \"Demo\",\n" +
                                                            "\"name\": \"User\",\n" +
                                                            "\"password\": \"demoUser$123\",\n" +
                                                            "\"email\": \"demoUser123@email.com\"\n}"
                                            )
                                    }
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            description = "User registration was successful",
                            responseCode = "201",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = UserResponseDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = "{\n\"id\": " +
                                                                    "\"ecf99a65-a677-45f4-b407-16ef672233fc\",\n" +
                                                                    "\"surname\": \"Demo\",\n" +
                                                                    "\"name\": \"User\",\n" +
                                                                    "\"username\": \"demoUser\",\n" +
                                                                    "\"email\": \"demoUser123@email.com\"\n}"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )
    @PostMapping("/public")
    public ResponseEntity<UserResponseDto> addUser(
            @RequestBody UserDto userDto
    ) {
        UserResponseDto persistedUser = userService.addUser(userDto);
        return new ResponseEntity<>(persistedUser,HttpStatus.CREATED);
    }

//    /**
//     * Endpoint for user's info retrieval from JWT claims
//     *
//     * @return user's data that corresponds to requested unique identifier and 200 status code in case of success
//     * @author FanTamas
//     */
//    @Operation(
//            description = "Endpoint for user's info retrieval from JWT claims.",
//            summary = "Retrieve user info.",
//            responses = {
//                    @ApiResponse(
//                            description = "User info retrieval was successful",
//                            responseCode = "200",
//                            content = {
//                                    @Content(
//                                            schema = @Schema(implementation = UserResponseDto.class),
//                                            examples = {
//                                                    @ExampleObject(
//                                                            value = "{\n\"id\": " +
//                                                                    "\"ecf99a65-a677-45f4-b407-16ef672233fc\",\n" +
//                                                                    "\"surname\": \"Demo\",\n" +
//                                                                    "\"name\": \"User\",\n" +
//                                                                    "\"username\": \"demoUser\",\n" +
//                                                                    "\"email\": \"demoUser123@email.com\"\n}"
//                                                    )
//                                            }
//                                    )
//                            }
//                    )
//            }
//    )
//    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
//    @GetMapping("/private")
//    public ResponseEntity<UserResponseDto> getUserInfo() {
//
//        UserResponseDto persistedUser = userService.getUserInfo();
//        return new ResponseEntity<>(persistedUser, HttpStatus.OK);
//    }
//
//    /**
//     * Endpoint for user's account and entity record from security provider
//     *
//     * @return 204 status code
//     * @author FanTamas
//     */
//    @Operation(
//            description = "Endpoint for user's account and entity record from security provider.",
//            summary = "Delete user info.",
//            responses = {
//                    @ApiResponse(
//                            description = "User info deletion was successful",
//                            responseCode = "204"
//                    )
//            }
//    )
//    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
//    @DeleteMapping("/private")
//    public ResponseEntity<?> deleteUserByUser() {
//
//        userService.deleteUserByUser();
//        return ResponseEntity.noContent().build();
//    }
}
