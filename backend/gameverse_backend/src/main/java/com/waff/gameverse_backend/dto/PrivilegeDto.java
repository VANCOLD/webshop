package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The PrivilegeDto class represents a Data Transfer Object (DTO) for privileges and permissions.
 * It is used to transfer privilege-related information between different layers of the application.
 */
@Setter
@Getter
public class PrivilegeDto implements Serializable {

    /**
     * The unique identifier for the privilege.
     */
    private Long id;

    /**
     * The name of the privilege.
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * Constructs an empty PrivilegeDto with default values (id=0, name="").
     */
    public PrivilegeDto() {
        this(0L, "");
    }

    /**
     * Constructs a PrivilegeDto with the specified name and default id (0).
     *
     * @param name The name of the privilege.
     */
    public PrivilegeDto(String name) {
        this(0L, name);
    }

    /**
     * Constructs a PrivilegeDto with the specified id and name.
     *
     * @param id   The unique identifier for the privilege.
     * @param name The name of the privilege.
     */
    public PrivilegeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
