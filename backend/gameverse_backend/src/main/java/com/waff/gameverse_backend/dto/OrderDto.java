package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {

    private Long id;

    @NotNull
    private UserDto user;

    @NotNull
    private String orderStatus;

    @NotNull
    @NotEmpty
    private List<OrderedProductDto> orderedProducts;

}
