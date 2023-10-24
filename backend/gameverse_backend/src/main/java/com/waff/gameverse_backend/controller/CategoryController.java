package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.CategoryDto;
import com.waff.gameverse_backend.model.Category;
import com.waff.gameverse_backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The CategoryController class handles operations related to categorys and permissions.
 */
@EnableMethodSecurity
@PreAuthorize("@tokenService.hasPrivilege('edit_products')")
@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Constructs a new CategoryController with the provided CategoryService.
     *
     * @param categoryService The CategoryService to use for managing categorys.
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Retrieves a list of all categorys.
     *
     * @return ResponseEntity<List<CategoryDto>> A ResponseEntity containing a list of CategoryDto objects.
     * @see CategoryDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> findAll() {
        var categorys = categoryService.findAll();

        if (categorys.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(categorys.stream().map(Category::convertToDto).toList());
        }
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return ResponseEntity<CategoryDto> A ResponseEntity containing the CategoryDto for the specified ID.
     * @throws NoSuchElementException if the category with the given ID does not exist.
     * @see CategoryDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Creates a new category.
     *
     * @param categoryDto The CategoryDto containing the category information to be created.
     * @return ResponseEntity<CategoryDto> A ResponseEntity containing the newly created CategoryDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the category.
     * @see CategoryDto
     */
    @PostMapping
    public ResponseEntity<CategoryDto> save(@Validated @RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.ok(categoryService.save(categoryDto.getName()).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing category.
     *
     * @param categoryDto The CategoryDto containing the updated category information.
     * @return ResponseEntity<CategoryDto> A ResponseEntity containing the updated CategoryDto.
     * @throws NoSuchElementException if the category to update does not exist.
     * @see CategoryDto
     */
    @PutMapping
    public ResponseEntity<CategoryDto> update(@Validated @RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.ok(categoryService.update(categoryDto).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @return ResponseEntity<CategoryDto> A ResponseEntity containing the deleted CategoryDto.
     * @throws NoSuchElementException if the category with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
