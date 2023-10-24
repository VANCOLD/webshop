package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code CategoryRepository} interface is a Spring Data JPA repository
 * responsible for managing database operations related to the {@link Category} entity.
 * It provides methods for common CRUD (Create, Read, Update, Delete) operations on category,
 * as well as a custom query method to find a categories by its name.
 *
 * <p>This repository is annotated with {@link org.springframework.stereotype.Repository}
 * to indicate that it is a Spring component and should be managed by the Spring framework.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Category
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Retrieve a category by its name.
     *
     * @param name The name of the category to search for.
     * @return An {@link java.util.Optional} containing the found category,
     *         or an empty {@code Optional} if no matching category is found.
     */
    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
