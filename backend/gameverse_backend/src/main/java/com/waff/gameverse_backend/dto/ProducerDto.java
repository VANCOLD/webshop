package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * The ProducerDto class represents a Data Transfer Object (DTO) for a producer in products {@Link Product}.
 * It is used to transfer privilege-related information between different layers of the application.
 */
@Setter
@Getter
public class ProducerDto {

    /**
     * The unique identifier for the producer.
     */
    private Long id;

    /**
     * The name of the producer.
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * Constructs an empty ProducerDto with default values (id=0, name="").
     */
    public ProducerDto() {
        this(0L, "");
    }

    /**
     * Constructs a ProducerDto with the specified name and default id (0).
     *
     * @param name The name of the producer.
     */
    public ProducerDto(String name) {
        this(0L, name);
    }

    /**
     * Constructs a ProducerDto with the specified id and name.
     *
     * @param id   The unique identifier for the producer.
     * @param name The name of the producer.
     */
    public ProducerDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
