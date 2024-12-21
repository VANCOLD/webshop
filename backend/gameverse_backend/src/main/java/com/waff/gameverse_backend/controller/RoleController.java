package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The RoleController class handles operations related to roles and permissions.
 */
@EnableMethodSecurity
@PreAuthorize("@tokenService.hasPrivilege('edit_users')")
@RequestMapping("/api/roles")
@RestController
public class RoleController {

    private final RoleService roleService;

    /**
     * Constructs a new RoleController with the provided RoleService.
     *
     * @param roleService The RoleService to use for managing roles.
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Retrieves a list of all roles.
     *
     * @return ResponseEntity<List<RoleDto>> A ResponseEntity containing a list of RoleDto objects.
     * @see RoleDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<RoleDto>> findAll() {
        var roles = roleService.findAll();

        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(roles.stream().map(Role::convertToDto).toList());
        }
    }

    /**
     * Retrieves a role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return ResponseEntity<RoleDto> A ResponseEntity containing the RoleDto for the specified ID.
     * @throws NoSuchElementException if the role with the given ID does not exist.
     * @see RoleDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(roleService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Creates a new role.
     *
     * @param roleDto The RoleDto containing the role information to be created.
     * @return ResponseEntity<RoleDto> A ResponseEntity containing the newly created RoleDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the role.
     * @see RoleDto
     */
    @PostMapping
    public ResponseEntity<RoleDto> save(@Validated @RequestBody RoleDto roleDto) {
        try {
            return ResponseEntity.ok(roleService.save(roleDto.getName()).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing role.
     *
     * @param roleDto The RoleDto containing the updated role information.
     * @return ResponseEntity<RoleDto> A ResponseEntity containing the updated RoleDto.
     * @throws IllegalArgumentException if there is a conflict or error while updating the role.
     * @throws NoSuchElementException if the role to update does not exist.
     * @see RoleDto
     */
    @PutMapping
    public ResponseEntity<RoleDto> update(@Validated @RequestBody RoleDto roleDto) {
        try {
            return ResponseEntity.ok(roleService.update(roleDto).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id The ID of the role to delete.
     * @return ResponseEntity<RoleDto> A ResponseEntity containing the deleted RoleDto.
     * @throws NoSuchElementException if the role with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RoleDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(roleService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
