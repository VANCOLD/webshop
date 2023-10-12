package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * The GenreDto class represents a Data Transfer Object (DTO) for a genre in products {@Link Product}.
 * It is used to transfer privilege-related information between different layers of the application.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenreDto {

    /**
     * The unique identifier for the genre.
     */
    private Long id;

    /**
     * The name of the genre.
     */
    @NotNull
    @NotEmpty
    private String name;

}
