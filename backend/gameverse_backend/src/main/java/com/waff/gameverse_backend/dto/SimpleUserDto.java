package com.waff.gameverse_backend.dto;

import com.waff.gameverse_backend.utils.SimpleDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimpleUserDto implements SimpleDto {

    private Long id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    private RoleDto role;
}
