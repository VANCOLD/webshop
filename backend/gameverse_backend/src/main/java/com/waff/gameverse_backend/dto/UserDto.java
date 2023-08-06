package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserDto implements Serializable {

    @Positive
    private Long id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    private RoleDto role;


    public UserDto() {
        this("", "", new RoleDto());
    }

    public UserDto(String username) {
        this(username, "", new RoleDto());
    }

    public UserDto(String username, String password) {
        this(username, password, new RoleDto());
    }

    public UserDto(String username, String password, RoleDto role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
