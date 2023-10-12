package com.waff.gameverse_backend.dto;

import com.waff.gameverse_backend.utils.SimpleDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
 /**
 * A DTO class that holds some information about a product, used for creation (simplified).
 **/
public class SimpleProductDto implements SimpleDto {

    private Long id;

    /** The name of the product. */
    @NotNull
    @NotEmpty
    private String name;

    /** A brief description of the product. */
    @NotNull
    @NotEmpty
    private String description;

    /** The price of the product. */
    @Positive
    private Double price;

    /** The URL or path to the product's image. */
    @NotNull
    @NotEmpty
    private String image;

    /** The tax rate applicable to the product. */
    @Positive
    private Integer tax;

    /** The available stock quantity of the product. */
    @PositiveOrZero
    private Integer stock;

    /** The Global Trade Item Number (GTIN) of the product. */
    @NotNull
    @NotEmpty
    private String gtin;


}
