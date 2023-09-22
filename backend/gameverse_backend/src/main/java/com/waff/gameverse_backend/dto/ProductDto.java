package com.waff.gameverse_backend.dto;



import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {


    private Long id;

    private String name;

    private String description;

    private Double price;

    private String image;

    private Byte tax;

    private Integer stock;

    private String gtin;

    private LocalDateTime available;

    private String esrbRating;

    private ConsoleGenerationDto consoleGeneration;

    private CategoryDto category;

    private ProducerDto producer;


    private List<GenreDto> genreList;
}
