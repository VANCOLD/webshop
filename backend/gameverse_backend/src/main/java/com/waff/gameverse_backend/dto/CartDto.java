package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {


    private Long id;

    @NotNull
    private UserDto user;

    @NotNull
    private List<ProductDto> products;

}
