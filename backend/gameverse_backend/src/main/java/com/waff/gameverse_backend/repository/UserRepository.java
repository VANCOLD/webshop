package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Privilege;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserRepository} interface is a Spring Data JPA repository
 * responsible for managing database operations related to the {@link User} entity.
 * It provides methods for common CRUD (Create, Read, Update, Delete) operations on users,
 * as well as a custom query method to find a users by its name.
 *
 * <p>This repository is annotated with {@link org.springframework.stereotype.Repository}
 * to indicate that it is a Spring component and should be managed by the Spring framework.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to find.
     * @return An {@link java.util.Optional} containing the found user,
     *         or an empty {@code Optional} if no matching user is found.
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
