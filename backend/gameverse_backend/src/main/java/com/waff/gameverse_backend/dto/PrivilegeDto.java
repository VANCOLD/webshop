package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PrivilegeDto implements Serializable {

    @Positive
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    public PrivilegeDto() {
        this("");
    }

    public PrivilegeDto(String name) {
        this.name = name;
    }
}
