package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddProductToCartDto {


    private Long productid;


    private Long userid;
}