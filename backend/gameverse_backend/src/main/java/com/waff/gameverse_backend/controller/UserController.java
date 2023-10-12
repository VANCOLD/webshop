package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.SimpleUserDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The UserController class handles operations related to user management.
 */
@EnableMethodSecurity
@PreAuthorize("@tokenService.hasPrivilege('edit_users')")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    /**
     * Constructs a new UserController with the provided UserService.
     *
     * @param userService The UserService to use for managing users.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return ResponseEntity<List<UserDto>> A ResponseEntity containing a list of UserDto objects.
     * @see UserDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<SimpleUserDto>> findAll() {
        var users = userService.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users.stream().map(User::convertToSimpleDto).toList());
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the UserDto for the specified ID.
     * @throws NoSuchElementException if the user with the given ID does not exist.
     * @see UserDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<SimpleUserDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id).convertToSimpleDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Creates a new user.
     *
     * @param userDto The SimpleUserDto containing the user information to be created.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the newly created UserDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the user.
     * @see SimpleUserDto
     */
    @PostMapping
    public ResponseEntity<SimpleUserDto> save(@Validated @RequestBody SimpleUserDto userDto) {
        try {
            return ResponseEntity.ok(userService.save(userDto).convertToSimpleDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing user.
     *
     * @param userDto The UserDto containing the updated user information.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the updated UserDto.
     * @throws NoSuchElementException if the user to update does not exist.
     * @see UserDto
     */
    @PutMapping
    public ResponseEntity<SimpleUserDto> update(@Validated @RequestBody SimpleUserDto userDto) {
        try {
            return ResponseEntity.ok(userService.update(userDto).convertToSimpleDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the deleted UserDto.
     * @throws NoSuchElementException if the user with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleUserDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.delete(id).convertToSimpleDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
