package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * The CategoryDto class represents a Data Transfer Object (DTO) for categories in products {@See Product}.
 * It is used to transfer category-related information between different layers of the application.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDto {

    /**
     * The unique identifier for the category.
     */
    private Long id;

    /**
     * The name of the category.
     */
    @NotNull
    @NotEmpty
    private String name;

    private List<ProductDto> products;


}
