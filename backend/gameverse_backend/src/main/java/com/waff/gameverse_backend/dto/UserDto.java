package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The UserDto class represents a Data Transfer Object (DTO) for user information.
 * It is used to transfer user-related information, including roles, between different layers of the application.
 */
@Setter
@Getter
public class UserDto implements Serializable {

    /**
     * The unique identifier for the user.
     */
    @Positive
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

    /**
     * The role associated with the user.
     */
    @NotNull
    private RoleDto role;

    /**
     * Constructs an empty UserDto with default values (id=0, username="", password="", role=empty RoleDto).
     */
    public UserDto() {
        this(0L, "", "", new RoleDto());
    }

    /**
     * Constructs a UserDto with the specified username and default id (0), password (""),
     * and role (empty RoleDto).
     *
     * @param username The username of the user.
     */
    public UserDto(String username) {
        this(0L, username, "", new RoleDto());
    }

    /**
     * Constructs a UserDto with the specified username, password, and default id (0) and role (empty RoleDto).
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public UserDto(String username, String password) {
        this(0L, username, password, new RoleDto());
    }

    /**
     * Constructs a UserDto with the specified id, username, password, and default role (empty RoleDto).
     *
     * @param id       The unique identifier for the user.
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public UserDto(Long id, String username, String password) {
        this(id, username, password, new RoleDto());
    }

    /**
     * Constructs a UserDto with the specified username, password, and role.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param role     The role associated with the user.
     */
    public UserDto(String username, String password, RoleDto role) {
        this(0L, username, password, role);
    }

    /**
     * Constructs a UserDto with the specified id, username, password, and role.
     *
     * @param id       The unique identifier for the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param role     The role associated with the user.
     */
    public UserDto(Long id, String username, String password, RoleDto role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
