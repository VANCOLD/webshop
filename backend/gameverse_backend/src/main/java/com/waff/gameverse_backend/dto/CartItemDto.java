package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Long id;

    @Positive
    private Integer amount;

    @NotNull
    private ProductDto product;

    @NotNull
    private CartDto cart;

}
