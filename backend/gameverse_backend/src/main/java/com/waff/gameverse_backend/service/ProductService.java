package com.waff.gameverse_backend.service;

import java.util.List;
import java.util.Optional;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.embedded.ProductType;
import com.waff.gameverse_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService
{

    @Autowired
    private ProductRepository prodRepo;

    @Autowired
    private GenreRepository genreRepo;

    @Autowired
    private ConsoleGenerationRepository conGenRepo;


    public List<Product> findAllProducts() {
        return prodRepo.findAll();
    }

    public Optional<Product> findProductById(long Id) {return prodRepo.findById(Id);}

    public List<Product> findProductByType(ProductType productType) {
        return prodRepo.findProductByType(productType);
    }

    public Product saveProductByType(Product product,ProductType productType) {
        product.setType(productType);
        return prodRepo.save(product);
    }
}
