package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code PrivilegeRepository} interface is a Spring Data JPA repository
 * responsible for managing database operations related to the {@link Privilege} entity.
 * It provides methods for common CRUD (Create, Read, Update, Delete) operations on privileges,
 * as well as a custom query method to find a privileges by its name.
 *
 * <p>This repository is annotated with {@link org.springframework.stereotype.Repository}
 * to indicate that it is a Spring component and should be managed by the Spring framework.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Privilege
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    /**
     * Find a privilege by its name.
     *
     * @param name The name of the privilege to find.
     * @return An {@link java.util.Optional} containing the found privilege,
     *         or an empty {@code Optional} if no matching privilege is found.
     */
    Optional<Privilege> findByName(String name);
}
