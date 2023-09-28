package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.GenreDto;
import com.waff.gameverse_backend.model.Genre;
import com.waff.gameverse_backend.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The GenreController class handles operations related to genres and permissions.
 */
@PreAuthorize("@tokenService.hasPrivilege('edit_products')")
@RequestMapping("/api/genres")
@RestController
public class GenreController {

    private final GenreService genreService;

    /**
     * Constructs a new GenreController with the provided GenreService.
     *
     * @param genreService The GenreService to use for managing genres.
     */
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Retrieves a list of all genres.
     *
     * @return ResponseEntity<List<GenreDto>> A ResponseEntity containing a list of GenreDto objects.
     * @see GenreDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<GenreDto>> findAll() {
        var genres = genreService.findAll();

        if (genres.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(genres.stream().map(Genre::convertToDto).toList());
        }
    }

    /**
     * Retrieves a genre by its ID.
     *
     * @param id The ID of the genre to retrieve.
     * @return ResponseEntity<GenreDto> A ResponseEntity containing the GenreDto for the specified ID.
     * @throws NoSuchElementException if the genre with the given ID does not exist.
     * @see GenreDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(genreService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Creates a new genre.
     *
     * @param genreDto The GenreDto containing the genre information to be created.
     * @return ResponseEntity<GenreDto> A ResponseEntity containing the newly created GenreDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the genre.
     * @see GenreDto
     */
    @PostMapping
    public ResponseEntity<GenreDto> save(@Validated @RequestBody GenreDto genreDto) {
        try {
            return ResponseEntity.ok(genreService.save(genreDto.getName()).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing genre.
     *
     * @param genreDto The GenreDto containing the updated genre information.
     * @return ResponseEntity<GenreDto> A ResponseEntity containing the updated GenreDto.
     * @throws NoSuchElementException if the genre to update does not exist.
     * @see GenreDto
     */
    @PutMapping
    public ResponseEntity<GenreDto> update(@Validated @RequestBody GenreDto genreDto) {
        try {
            return ResponseEntity.ok(genreService.update(genreDto).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a genre by its ID.
     *
     * @param id The ID of the genre to delete.
     * @return ResponseEntity<GenreDto> A ResponseEntity containing the deleted GenreDto.
     * @throws NoSuchElementException if the genre with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<GenreDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(genreService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
