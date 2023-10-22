package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.dto.ProducerDto;
import com.waff.gameverse_backend.model.Address;
import com.waff.gameverse_backend.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ProducerRepository} interface is a Spring Data JPA repository responsible for managing
 * database operations related to the {@link Producer} entity. It provides methods for common CRUD
 * (Create, Read, Update, Delete) operations on producers, as well as a custom query method to find a
 * producer by its name.
 *
 * <p>This repository is annotated with {@link org.springframework.stereotype.Repository} to
 * indicate that it is a Spring component and should be managed by the Spring framework.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Producer
 */
@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    /**
     * Retrieve a producer by its name.
     *
     * @param name The name of the producer to search for.
     * @return An {@link java.util.Optional} containing the found producer, or an empty {@code Optional}
     * if no matching producer is found.
     */
    Optional<Producer> findByName(String name);

    List<Producer> findAllByAddress(Address address);

    boolean existsByName(String name);
}
