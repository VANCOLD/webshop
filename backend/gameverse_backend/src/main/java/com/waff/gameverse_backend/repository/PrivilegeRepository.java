package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The PrivilegeRepository interface provides CRUD (Create, Read, Update, Delete) operations for Privilege entities.
 * It extends the JpaRepository interface to work with Privilege entities and allows querying the database for privileges.
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    /**
     * Find a privilege by its name.
     *
     * @param name The name of the privilege to find.
     * @return An Optional containing the found Privilege, or an empty Optional if not found.
     */
    Optional<Privilege> findByName(String name);
}
