package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Producer;
import com.waff.gameverse_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * The {@code ProductRepository} interface is a Spring Data JPA repository responsible for managing
 * database operations related to the {@link Product} entity. It provides methods for common CRUD
 * (Create, Read, Update, Delete) operations on producet, as well as a custom query method to find a
 * product by its name.
 *
 * <p>This repository is annotated with {@link org.springframework.stereotype.Repository} to
 * indicate that it is a Spring component and should be managed by the Spring framework.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Retrieve a product by its name.
     *
     * @param name The name of the product to search for.
     * @return An {@link java.util.Optional} containing the found product, or an empty {@code Optional}
     * if no matching product is found.
     */
    Optional<Product> findByName(String name);
}
