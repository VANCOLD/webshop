package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Product;
import com.waff.gameverse_backend.repository.*;
import com.waff.gameverse_backend.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/products")
@AllArgsConstructor @NoArgsConstructor
public class ProductController<T extends Product>
{

    private ProductService prodService;

    /*
    @GetMapping
    public List<T> findAll() {
        return prodService.findAll();
    }

    @PostMapping
    public ResponseEntity<T> createProduct(@RequestBody T product) {
        product = (T) prodRepo.save(product);
        return ResponseEntity.created(URI.create("http://localhost:808k0/products")).body(product);
    }*/

}
