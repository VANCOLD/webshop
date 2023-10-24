package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * The RoleDto class represents a Data Transfer Object (DTO) for roles.
 * It is used to transfer role-related information, including privileges, between different layers of the application.
 */
@Setter
@Getter
@ToString
public class RoleDto implements Serializable {

    /**
     * The unique identifier for the role.
     */
    private Long id;

    /**
     * The name of the role.
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * The list of privileges associated with the role.
     */
    @NotNull
    private List<PrivilegeDto> privileges;

    /**
     * Constructs an empty RoleDto with default values (id=0, name="", privileges=[]).
     */
    public RoleDto() {
        this(0L, "", List.of());
    }

    /**
     * Constructs a RoleDto with the specified name and default id (0) and privileges (empty list).
     *
     * @param name The name of the role.
     */
    public RoleDto(String name) {
        this(0L, name, List.of());
    }

    /**
     * Constructs a RoleDto with the specified id, name, and default privileges (empty list).
     *
     * @param id   The unique identifier for the role.
     * @param name The name of the role.
     */
    public RoleDto(Long id, String name) {
        this(id, name, List.of());
    }

    /**
     * Constructs a RoleDto with the specified name and privileges.
     *
     * @param name       The name of the role.
     * @param privileges The list of privileges associated with the role.
     */
    public RoleDto(String name, List<PrivilegeDto> privileges) {
        this(0L, name, privileges);
    }

    /**
     * Constructs a RoleDto with the specified id, name, and privileges.
     *
     * @param id         The unique identifier for the role.
     * @param name       The name of the role.
     * @param privileges The list of privileges associated with the role.
     */
    public RoleDto(Long id, String name, List<PrivilegeDto> privileges) {
        this.id = id;
        this.name = name;
        this.privileges = privileges;
    }
}
