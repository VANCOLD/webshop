package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * The ConsoleGenerationDto class represents a Data Transfer Object (DTO) for a console generation in products {@Link Product}.
 * It is used to transfer privilege-related information between different layers of the application.
 */
@Setter
@Getter
public class ConsoleGenerationDto {

    /**
     * The unique identifier for the console generation.
     */
    @Positive
    private Long id;

    /**
     * The name of the console generation.
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * Constructs an empty ConsoleGenerationDto with default values (id=0, name="").
     */
    public ConsoleGenerationDto() {
        this(0L, "");
    }

    /**
     * Constructs a ConsoleGenerationDto with the specified name and default id (0).
     *
     * @param name The name of the console generation.
     */
    public ConsoleGenerationDto(String name) {
        this(0L, name);
    }

    /**
     * Constructs a ConsoleGenerationDto with the specified id and name.
     *
     * @param id   The unique identifier for the console generation.
     * @param name The name of the console generation.
     */
    public ConsoleGenerationDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
