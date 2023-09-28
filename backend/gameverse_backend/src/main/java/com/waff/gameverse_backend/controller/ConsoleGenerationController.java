package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.ConsoleGenerationDto;
import com.waff.gameverse_backend.model.ConsoleGeneration;
import com.waff.gameverse_backend.service.ConsoleGenerationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The ConsoleGenerationController class handles operations related to consoleGenerations and permissions.
 */
@PreAuthorize("@tokenService.hasPrivilege('edit_products')")
@RequestMapping("/api/console_generations")
@RestController
public class ConsoleGenerationController {

    private final ConsoleGenerationService consoleGenerationService;

    /**
     * Constructs a new ConsoleGenerationController with the provided ConsoleGenerationService.
     *
     * @param consoleGenerationService The ConsoleGenerationService to use for managing consoleGenerations.
     */
    public ConsoleGenerationController(ConsoleGenerationService consoleGenerationService) {
        this.consoleGenerationService = consoleGenerationService;
    }

    /**
     * Retrieves a list of all consoleGenerations.
     *
     * @return ResponseEntity<List<ConsoleGenerationDto>> A ResponseEntity containing a list of ConsoleGenerationDto objects.
     * @see ConsoleGenerationDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<ConsoleGenerationDto>> findAll() {
        var consoleGenerations = consoleGenerationService.findAll();

        if (consoleGenerations.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(consoleGenerations.stream().map(ConsoleGeneration::convertToDto).toList());
        }
    }

    /**
     * Retrieves a consoleGeneration by its ID.
     *
     * @param id The ID of the consoleGeneration to retrieve.
     * @return ResponseEntity<ConsoleGenerationDto> A ResponseEntity containing the ConsoleGenerationDto for the specified ID.
     * @throws NoSuchElementException if the consoleGeneration with the given ID does not exist.
     * @see ConsoleGenerationDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsoleGenerationDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(consoleGenerationService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Creates a new consoleGeneration.
     *
     * @param consoleGenerationDto The ConsoleGenerationDto containing the consoleGeneration information to be created.
     * @return ResponseEntity<ConsoleGenerationDto> A ResponseEntity containing the newly created ConsoleGenerationDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the consoleGeneration.
     * @see ConsoleGenerationDto
     */
    @PostMapping
    public ResponseEntity<ConsoleGenerationDto> save(@Validated @RequestBody ConsoleGenerationDto consoleGenerationDto) {
        try {
            return ResponseEntity.ok(consoleGenerationService.save(consoleGenerationDto.getName()).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing consoleGeneration.
     *
     * @param consoleGenerationDto The ConsoleGenerationDto containing the updated consoleGeneration information.
     * @return ResponseEntity<ConsoleGenerationDto> A ResponseEntity containing the updated ConsoleGenerationDto.
     * @throws NoSuchElementException if the consoleGeneration to update does not exist.
     * @see ConsoleGenerationDto
     */
    @PutMapping
    public ResponseEntity<ConsoleGenerationDto> update(@Validated @RequestBody ConsoleGenerationDto consoleGenerationDto) {
        try {
            return ResponseEntity.ok(consoleGenerationService.update(consoleGenerationDto).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a consoleGeneration by its ID.
     *
     * @param id The ID of the consoleGeneration to delete.
     * @return ResponseEntity<ConsoleGenerationDto> A ResponseEntity containing the deleted ConsoleGenerationDto.
     * @throws NoSuchElementException if the consoleGeneration with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ConsoleGenerationDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(consoleGenerationService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
