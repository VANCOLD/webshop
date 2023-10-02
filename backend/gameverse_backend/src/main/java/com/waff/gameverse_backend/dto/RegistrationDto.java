package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * The RegistrationDto class represents a Data Transfer Object (DTO) for user registration information.
 * It is used to transfer registration-related information between different layers of the application.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto implements Serializable {

    /**
     * The username for user registration.
     */
    @NotNull
    @NotEmpty
    String username;

    /**
     * The password for user registration.
     */
    @NotNull
    @NotEmpty
    String password;
}
