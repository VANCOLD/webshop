package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The UserRepository interface provides CRUD (Create, Read, Update, Delete) operations for User entities.
 * It extends the JpaRepository interface to work with User entities and allows querying the database for users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the found User, or an empty Optional if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Find all users who have a specific role.
     *
     * @param role The role to search for.
     * @return A list of users with the specified role.
     */
    List<User> findAllByRole(Role role);
}
