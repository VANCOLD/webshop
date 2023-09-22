package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * The GenreDto class represents a Data Transfer Object (DTO) for a genre in products {@Link Product}.
 * It is used to transfer privilege-related information between different layers of the application.
 */
@Setter
@Getter
public class GenreDto {

    /**
     * The unique identifier for the genre.
     */
    @Positive
    private Long id;

    /**
     * The name of the genre.
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * Constructs an empty GenreDto with default values (id=0, name="").
     */
    public GenreDto() {
        this(0L, "");
    }

    /**
     * Constructs a GenreDto with the specified name and default id (0).
     *
     * @param name The name of the genre.
     */
    public GenreDto(String name) {
        this(0L, name);
    }

    /**
     * Constructs a GenreDto with the specified id and name.
     *
     * @param id   The unique identifier for the genre.
     * @param name The name of the genre.
     */
    public GenreDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
