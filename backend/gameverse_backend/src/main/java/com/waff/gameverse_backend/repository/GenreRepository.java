package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code GenreRepository} interface is a Spring Data JPA repository responsible for managing
 * database operations related to the {@link Genre} entity. It provides methods for common CRUD
 * (Create, Read, Update, Delete) operations on genres, as well as a custom query method to find a
 * genre by its name.
 *
 * <p>This repository is annotated with {@link org.springframework.stereotype.Repository} to
 * indicate that it is a Spring component and should be managed by the Spring framework.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Genre
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    /**
     * Retrieve a genre by its name.
     *
     * @param name The name of the genre to search for.
     * @return An {@link java.util.Optional} containing the found genre, or an empty {@code Optional}
     *         if no matching genre is found.
     */
    Optional<Genre> findByName(String name);

}
