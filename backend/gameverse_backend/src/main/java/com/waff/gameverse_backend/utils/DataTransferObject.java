package com.waff.gameverse_backend.utils;

/**
 * The DataTransferObject interface defines a method for converting an entity to its corresponding DTO (Data Transfer Object).
 * Implement this interface in entity classes to provide a convenient way to transform the entity data into a DTO.
 *
 * @param <T> The type of DTO to which the entity can be converted.
 */
public interface DataTransferObject<T> {

    /**
     * Convert the entity to its corresponding DTO representation.
     *
     * @return The DTO representing the entity.
     */
    T convertToDto();
}
