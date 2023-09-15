package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * DataTransferObject für Rollen, dieses wird nur genutzt,
 * wenn wir den User an das Frontend geben.
 */
@Setter
@Getter
public class RoleDto implements Serializable {

    @Positive
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private List<PrivilegeDto> privileges;

    public RoleDto() {
        this(0L,"", List.of());
    }

    public RoleDto(String name) {
        this(0L,name, List.of());
    }

    public RoleDto(String name, List<PrivilegeDto> privileges) {
        this(0L, name, privileges);
    }

    public RoleDto(Long id, String name, List<PrivilegeDto> privileges) {
        this.id = id;
        this.name = name;
        this.privileges = privileges;
    }
}