package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.embedded.ProductType;
import com.waff.gameverse_backend.service.ProductService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController
{

    private final ProductService prodService;

    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }


    @GetMapping
    public List<Product> findAllProducts() {
        return this.prodService.findAllProducts();
    }

    @GetMapping("/byId/{id}")
    public Optional<Product> findProductById(@PathVariable Integer id) {
        return prodService.findProductById(id);
    }

    @GetMapping("/{classType}")
    public List<Product> findAllProductsFromType(@PathVariable String classType) throws ClassNotFoundException
    {
        if(classType.equalsIgnoreCase("products"))
            return new ArrayList<>();

        ProductType pt = new ProductType(Class.forName("com.waff.gameverse_backend.datamodel." + StringUtils.capitalize(classType)));
        return prodService.findProductByType(pt);
    }


    @PostMapping("/{classType}")
    public Product saveProductByType(@PathVariable String classType,@RequestBody Product product) throws ClassNotFoundException
    {
        if(classType.equalsIgnoreCase("product"))
            return new Product();

        ProductType pt = new ProductType(Class.forName("com.waff.gameverse_backend.datamodel."+ StringUtils.capitalize(classType)));
        return this.prodService.saveProductByType(product,pt);
    }

}