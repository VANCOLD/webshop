package com.waff.gameverse_backend.Controller;

import com.waff.gameverse_backend.Datenmodell.Product;
import com.waff.gameverse_backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ProductController
{

    @Autowired
    private ProductRepository repository;

    @GetMapping("/QuickSearch")
    public Iterable<Product> findProduct(String text) {
        return new ArrayList<Product>();
    }
}
