package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.*;
import com.waff.gameverse_backend.model.*;
import com.waff.gameverse_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DisplayController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final GenreService genreService;

    private final ConsoleGenerationService consoleGenerationService;

    private final ProducerService producerService;


    public DisplayController( ProductService productService,
                              ProducerService producerService,
                              GenreService genreService,
                              CategoryService categoryService,
                              ConsoleGenerationService consoleGenerationService) {

        this.productService           = productService;
        this.producerService          = producerService;
        this.genreService             = genreService;
        this.consoleGenerationService = consoleGenerationService;
        this.categoryService          = categoryService;

    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> listAllProdcuts () {
        return ResponseEntity.ok(productService.findAll().stream().map(Product::convertToDto).toList());
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> listProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.findById(productId).convertToDto());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> listAllCategories() {
        return ResponseEntity.ok(categoryService.findAll().stream().map(Category::convertToDto).toList());
    }

    @GetMapping("/producers")
    public ResponseEntity<List<ProducerDto>> listAllProducers() {
        return ResponseEntity.ok(producerService.findAll().stream().map(Producer::convertToDto).toList());
    }

    @GetMapping("/console_generations")
    public ResponseEntity<List<ConsoleGenerationDto>> listAllConsoleGenerations() {
        return ResponseEntity.ok(consoleGenerationService.findAll().stream().map(ConsoleGeneration::convertToDto).toList());
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> listAllGenres() {
        return ResponseEntity.ok(genreService.findAll().stream().map(Genre::convertToDto).toList());
    }
}
