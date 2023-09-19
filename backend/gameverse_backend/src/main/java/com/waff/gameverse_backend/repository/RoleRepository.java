package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Privilege;
import com.waff.gameverse_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The {@code uRoleRepository} interface is a Spring Data JPA repository
 * responsible for managing database operations related to the {@link Role} entity.
 * It provides methods for common CRUD (Create, Read, Update, Delete) operations on roles,
 * as well as a custom query method to find a role by its name.
 *
 * <p>This repository is annotated with {@link org.springframework.stereotype.Repository}
 * to indicate that it is a Spring component and should be managed by the Spring framework.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Role
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role by its name.
     *
     * @param name The name of the role to find.
     * @return An {@link java.util.Optional} containing the found role,
     *         or an empty {@code Optional} if no matching role is found.
     */
    Optional<Role> findByName(String name);

    /**
     * Find all roles that have a specific privilege.
     *
     * @param privilege The privilege to search for.
     * @return A list of roles containing the specified privilege.
     */
    List<Role> findAllByPrivileges(Privilege privilege);
}
