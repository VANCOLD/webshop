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
        this(0L, "", "", new RoleDto());
    }

    public UserDto(String username) {
        this(0L, username, "", new RoleDto());
    }

    public UserDto(String username, String password) {
        this(0L, username, password, new RoleDto());
    }

    public UserDto(String username, String password, RoleDto role) {
        this(0L, username,password,role);
    }

    public UserDto(Long id, String username, String password, RoleDto role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
