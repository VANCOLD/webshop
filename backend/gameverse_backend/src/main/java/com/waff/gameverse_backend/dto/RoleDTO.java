package com.waff.gameverse_backend.dto;


import com.waff.gameverse_backend.datamodel.Privileges;
import com.waff.gameverse_backend.datamodel.Roles;
import com.waff.gameverse_backend.util.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class RoleDTO implements DTO<RoleDTO, Roles> {

    @NotNull @NotBlank @NotEmpty
    private String name;

    @NotNull @NotBlank @NotEmpty
    private Set<Privileges> privileges;

    @Override
    public RoleDTO convertToDto(Roles role) {
        return new RoleDTO(role.getName(),role.getPrivilege());
    }

}
