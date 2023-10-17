package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * The UserDto class represents a Data Transfer Object (DTO) for user information.
 * It is used to transfer user-related information, including roles, between different layers of the application.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto implements Serializable {

    /**
     * The unique identifier for the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    @NotNull
    @NotEmpty
    private String username;

    /**
     * The password of the user.
     */
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String gender;

    @NotNull
    @NotEmpty
    private String firstname;

    @NotNull
    @NotEmpty
    private String lastname;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    private AddressDto address;

    @NotNull
    private RoleDto role;

}
