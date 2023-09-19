package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.PrivilegeDto;
import com.waff.gameverse_backend.model.Privilege;
import com.waff.gameverse_backend.service.PrivilegeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The PrivilegeController class handles operations related to privileges and permissions.
 */
@PreAuthorize("@tokenService.hasPrivilege('edit_users')")
@RequestMapping("/api/privileges")
@RestController
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    /**
     * Constructs a new PrivilegeController with the provided PrivilegeService.
     *
     * @param privilegeService The PrivilegeService to use for managing privileges.
     */
    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    /**
     * Retrieves a list of all privileges.
     *
     * @return ResponseEntity<List<PrivilegeDto>> A ResponseEntity containing a list of PrivilegeDto objects.
     * @see PrivilegeDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<PrivilegeDto>> findAll() {
        var privileges = privilegeService.findAll();

        if (privileges.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(privileges.stream().map(Privilege::convertToDto).toList());
        }
    }

    /**
     * Retrieves a privilege by its ID.
     *
     * @param id The ID of the privilege to retrieve.
     * @return ResponseEntity<PrivilegeDto> A ResponseEntity containing the PrivilegeDto for the specified ID.
     * @throws NoSuchElementException if the privilege with the given ID does not exist.
     * @see PrivilegeDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrivilegeDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(privilegeService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Creates a new privilege.
     *
     * @param privilegeDto The PrivilegeDto containing the privilege information to be created.
     * @return ResponseEntity<PrivilegeDto> A ResponseEntity containing the newly created PrivilegeDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the privilege.
     * @see PrivilegeDto
     */
    @PostMapping
    public ResponseEntity<PrivilegeDto> save(@RequestBody PrivilegeDto privilegeDto) {
        try {
            return ResponseEntity.ok(privilegeService.save(privilegeDto.getName()).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing privilege.
     *
     * @param privilegeDto The PrivilegeDto containing the updated privilege information.
     * @return ResponseEntity<PrivilegeDto> A ResponseEntity containing the updated PrivilegeDto.
     * @throws IllegalArgumentException if there is a conflict or error while updating the privilege.
     * @throws NoSuchElementException if the privilege to update does not exist.
     * @see PrivilegeDto
     */
    @PutMapping
    public ResponseEntity<PrivilegeDto> update(@RequestBody PrivilegeDto privilegeDto) {
        try {
            return ResponseEntity.ok(privilegeService.update(privilegeDto).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a privilege by its ID.
     *
     * @param id The ID of the privilege to delete.
     * @return ResponseEntity<PrivilegeDto> A ResponseEntity containing the deleted PrivilegeDto.
     * @throws NoSuchElementException if the privilege with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<PrivilegeDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(privilegeService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
