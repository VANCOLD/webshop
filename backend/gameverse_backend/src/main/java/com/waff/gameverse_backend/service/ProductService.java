package com.waff.gameverse_backend.service;

import java.util.List;
import java.util.Optional;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class ProductService
{

    private ProductRepository prodRepo;

    private GenreRepository genreRepo;

    private ConsoleGenerationRepository conGenRepo;


    public ProductService(ProductRepository prodRepo, GenreRepository genreRepo, ConsoleGenerationRepository conGenRepo)
    {
        this.prodRepo       = prodRepo;
        this.genreRepo      = genreRepo;
        this.conGenRepo     = conGenRepo;
    }

    public List<Product> findAllProducts() {
        return prodRepo.findAll();
    }

    public Optional<Product> findProductById(long Id) {return prodRepo.findById(Id);}

    public Product saveProduct(Product product) {return (Product) prodRepo.save(product);}

    public Product deleteProduct(Product product)
    {
        prodRepo.delete(product);
        return product;
    }

}
