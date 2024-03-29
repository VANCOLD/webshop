package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.ProductDto;
import com.waff.gameverse_backend.enums.EsrbRating;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * The ProductController class handles operations related to products and permissions.
 */
@EnableMethodSecurity
@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    /**
     * Constructs a new ProductController with the provided ProductService.
     *
     * @param productService The ProductService to use for managing products.
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves a list of all products.
     *
     * @return ResponseEntity<List<ProductDto>> A ResponseEntity containing a list of ProductDto objects.
     * @see ProductDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> findAll() {
        var products = productService.findAll();

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products.stream().map(Product::convertToDto).toList());
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity<ProductDto> A ResponseEntity containing the ProductDto for the specified ID.
     * @throws NoSuchElementException if the product with the given ID does not exist.
     * @see ProductDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/esrb")
    public ResponseEntity<List<String>> getEsrbRatings() {
        return ResponseEntity.ok(Stream.of(EsrbRating.values()).map(EsrbRating::getName).toList());
    }

    /**
     * Creates a new product.
     *
     * @param productDto The ProductDto containing the product information to be created.
     * @return ResponseEntity<ProductDto> A ResponseEntity containing the newly created ProductDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the product.
     */
    @PostMapping
    @PreAuthorize("@tokenService.hasPrivilege('edit_products')")
    public ResponseEntity<ProductDto> save(@Validated @RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.save(productDto).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing product.
     *
     * @param productDto The ProductDto containing the updated product information.
     * @return ResponseEntity<ProductDto> A ResponseEntity containing the updated ProductDto.
     * @throws NoSuchElementException if the product to update does not exist.
     * @see ProductDto
     */
    @PutMapping
    @PreAuthorize("@tokenService.hasPrivilege('edit_products')")
    public ResponseEntity<ProductDto> update(@Validated @RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.update(productDto).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return ResponseEntity<ProductDto> A ResponseEntity containing the deleted ProductDto.
     * @throws NoSuchElementException if the product with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@tokenService.hasPrivilege('edit_products')")
    public ResponseEntity<ProductDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

