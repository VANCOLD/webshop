package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The CategoryDto class represents a Data Transfer Object (DTO) for categories in products {@See Product}.
 * It is used to transfer category-related information between different layers of the application.
 */
@Setter
@Getter
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

    /**
     * Constructs an empty CategoryDto with default values (id=0, name="").
     */
    public CategoryDto() {
        this(0L, "");
    }

    /**
     * Constructs a CategoryDto with the specified name and default id (0).
     *
     * @param name The name of the category.
     */
    public CategoryDto(String name) {
        this(0L, name);
    }

    /**
     * Constructs a CategoryDto with the specified id and name.
     *
     * @param id   The unique identifier for the category.
     * @param name The name of the category.
     */
    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
