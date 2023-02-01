package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Product;
import com.waff.gameverse_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController
{

    @Autowired
    private ProductRepository prodRepo;

    @GetMapping
    public List<Product> findAll() {
        return prodRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product = (Product) prodRepo.save(product);
        return ResponseEntity.created(URI.create("http://localhost:808k0/products")).body(product);
    }

}
