package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * The ProducerDto class represents a Data Transfer Object (DTO) for a producer in products {@Link Product}.
 * It is used to transfer privilege-related information between different layers of the application.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    @NotNull
    private AddressDto address;

}
