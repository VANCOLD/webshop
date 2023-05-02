package com.waff.gameverse_backend.dto;


import com.waff.gameverse_backend.datamodel.Privileges;
import com.waff.gameverse_backend.datamodel.Roles;
import com.waff.gameverse_backend.datamodel.User;
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
public class LoginDTO implements DTO<LoginDTO,User> {

    @NotNull @NotBlank @NotEmpty
    private String username;

    @NotNull @NotBlank @NotEmpty
    private String password;

    @NotNull @NotEmpty
    private Set<Privileges> privileges;

    @Override
    public LoginDTO convertToDto(User user) {

        Set<Roles> roles = user.getRoles();
        Set<Privileges> privilegesSet = Set.of();

        for(Roles role: roles)
            privilegesSet.addAll(role.getPrivilege());


        return new LoginDTO(user.getUsername(), user.getPassword(), privilegesSet);
    }

}
