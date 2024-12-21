package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * The ConsoleGenerationDto class represents a Data Transfer Object (DTO) for a console generation in products {@Link Product}.
 * It is used to transfer privilege-related information between different layers of the application.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsoleGenerationDto {

    /**
     * The unique identifier for the console generation.
     */
    private Long id;

    /**
     * The name of the console generation.
     */
    @NotNull
    @NotEmpty
    private String name;
}
