package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDto {

    private Long id;

    @NotNull
    private SimpleUserDto user;

    @NotNull
    private List<ProductDto> products;

}
