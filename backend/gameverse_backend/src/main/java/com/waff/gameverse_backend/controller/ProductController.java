package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController
{

    @Autowired
    private final ProductService prodService;

    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }


    @GetMapping
    public List<Product> findAllProducts() {
        return this.prodService.findAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> findProductById(@PathVariable Integer id) {
        return prodService.findProductById(id);
    }



}