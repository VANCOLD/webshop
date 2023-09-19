package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Privilege;
import com.waff.gameverse_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The RoleRepository interface provides CRUD (Create, Read, Update, Delete) operations for Role entities.
 * It extends the JpaRepository interface to work with Role entities and allows querying the database for roles.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role by its name.
     *
     * @param name The name of the role to find.
     * @return An Optional containing the found Role, or an empty Optional if not found.
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
