package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.ConsoleGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * The {@code ConsoleGenerationRepository} interface is a Spring Data JPA repository
 * responsible for managing database operations related to the {@link ConsoleGeneration} entity.
 * It provides methods for common CRUD (Create, Read, Update, Delete) operations on console generations,
 * as well as a custom query method to find a console generation by its name.
 *
 * <p>This repository is annotated with {@link org.springframework.stereotype.Repository}
 * to indicate that it is a Spring component and should be managed by the Spring framework.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see ConsoleGeneration
 */
@Repository
public interface ConsoleGenerationRepository extends JpaRepository<ConsoleGeneration, Long> {

    /**
     * Retrieve a console generation by its name.
     *
     * @param name The name of the console generation to search for.
     * @return An {@link java.util.Optional} containing the found console generation,
     *         or an empty {@code Optional} if no matching console generation is found.
     */
    Optional<ConsoleGeneration> findByName(String name);

}
