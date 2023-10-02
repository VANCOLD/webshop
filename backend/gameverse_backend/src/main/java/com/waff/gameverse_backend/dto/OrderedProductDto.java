package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
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
public class OrderedProductDto {

    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @Positive
    private Double price;

    @Positive
    private Integer tax;

    @Positive
    private Integer amount;

}
