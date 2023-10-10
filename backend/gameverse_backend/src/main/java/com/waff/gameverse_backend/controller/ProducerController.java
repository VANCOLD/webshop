package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.ProducerDto;
import com.waff.gameverse_backend.model.Producer;
import com.waff.gameverse_backend.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The ProducerController class handles operations related to producers and permissions.
 */
@EnableMethodSecurity
@PreAuthorize("@tokenService.hasPrivilege('edit_products')")
@RequestMapping("/api/producers")
@RestController
public class ProducerController {

    private final ProducerService producerService;

    /**
     * Constructs a new ProducerController with the provided ProducerService.
     *
     * @param producerService The ProducerService to use for managing producers.
     */
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    /**
     * Retrieves a list of all producers.
     *
     * @return ResponseEntity<List<ProducerDto>> A ResponseEntity containing a list of ProducerDto objects.
     * @see ProducerDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProducerDto>> findAll() {
        var producers = producerService.findAll();

        if (producers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(producers.stream().map(Producer::convertToDto).toList());
        }
    }

    /**
     * Retrieves a producer by its ID.
     *
     * @param id The ID of the producer to retrieve.
     * @return ResponseEntity<ProducerDto> A ResponseEntity containing the ProducerDto for the specified ID.
     * @throws NoSuchElementException if the producer with the given ID does not exist.
     * @see ProducerDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProducerDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(producerService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Creates a new producer.
     *
     * @param producerDto The ProducerDto containing the producer information to be created.
     * @return ResponseEntity<ProducerDto> A ResponseEntity containing the newly created ProducerDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the producer.
     * @see ProducerDto
     */
    @PostMapping
    public ResponseEntity<ProducerDto> save(@Validated @RequestBody ProducerDto producerDto) {
        try {
            return ResponseEntity.ok(producerService.save(producerDto.getName()).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing producer.
     *
     * @param producerDto The ProducerDto containing the updated producer information.
     * @return ResponseEntity<ProducerDto> A ResponseEntity containing the updated ProducerDto.
     * @throws NoSuchElementException if the producer to update does not exist.
     * @see ProducerDto
     */
    @PutMapping
    public ResponseEntity<ProducerDto> update(@Validated @RequestBody ProducerDto producerDto) {
        try {
            return ResponseEntity.ok(producerService.update(producerDto).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a producer by its ID.
     *
     * @param id The ID of the producer to delete.
     * @return ResponseEntity<ProducerDto> A ResponseEntity containing the deleted ProducerDto.
     * @throws NoSuchElementException if the producer with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ProducerDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(producerService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

