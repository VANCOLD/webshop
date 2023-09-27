package com.waff.gameverse_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO class that holds information about a product.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    /** The unique identifier of the product. */
    private Long id;

    /** The name of the product. */
    private String name;

    /** A brief description of the product. */
    private String description;

    /** The price of the product. */
    private Double price;

    /** The URL or path to the product's image. */
    private String image;

    /** The tax rate applicable to the product. */
    private Byte tax;

    /** The available stock quantity of the product. */
    private Integer stock;

    /** The Global Trade Item Number (GTIN) of the product. */
    private String gtin;

    /** The date and time when the product is available. */
    private LocalDateTime available;

    /** The Entertainment Software Rating Board (ESRB) rating of the product. */
    private String esrbRating;

    /** The console generation associated with the product. */
    private ConsoleGenerationDto consoleGeneration;

    /** The list of categories to which the product belongs. */
    private CategoryDto category;

    /** The producer or manufacturer of the product. */
    private ProducerDto producer;

    /** The list of genres associated with the product. */
    private List<GenreDto> genreList;
}
