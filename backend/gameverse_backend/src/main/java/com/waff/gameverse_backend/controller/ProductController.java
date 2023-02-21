package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController
{

    private ProductService prodService;

    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }


    @GetMapping
    public List<Product> findAllProducts() { return prodService.findAllProducts(); }

    @GetMapping("/{id}")
    public Optional<Product> findProductById(@PathVariable long Id) { return prodService.findProductById(Id); }

    @PostMapping
    public Product saveProduct(@RequestBody @Valid Product product) {return (Product) prodService.saveProduct(product);}

    @DeleteMapping
    public Product deleteProduct(@RequestBody @Valid Product product) {
        return prodService.deleteProduct(product);
    }


}