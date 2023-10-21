package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddProductToCartDto {


    @Positive
    private Long productId;


    @Positive
    private Long userId;
}