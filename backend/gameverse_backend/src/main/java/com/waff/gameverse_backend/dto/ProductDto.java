package com.waff.gameverse_backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * A DTO class that holds information about a product.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto implements Serializable {

    /** The unique identifier of the product. */
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

    /** The date and time when the product is available. */
    private LocalDateTime available;

    /** The Entertainment Software Rating Board (ESRB) rating of the product. */
    private String esrbRating;

    /** The console generation associated with the product. */
    private ConsoleGenerationDto consoleGeneration;

    /** The list of categories to which the product belongs. */
    @NotNull
    private CategoryDto category;

    /** The producer or manufacturer of the product. */
    @NotNull
    private ProducerDto producer;

    /** The list of genres associated with the product. */
    private List<GenreDto> genres;

}
