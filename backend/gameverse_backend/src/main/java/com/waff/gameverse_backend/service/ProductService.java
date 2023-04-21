package com.waff.gameverse_backend.service;

import java.util.List;
import java.util.Optional;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class ProductService
{

    private final ProductRepository prodRepo;

    private final GenreRepository genreRepo;

    private final ConsoleGenerationRepository conGenRepo;

    public ProductService(ProductRepository prodRepository, GenreRepository genreRepo, ConsoleGenerationRepository conGenRepo) {
        this.prodRepo   = prodRepository;
        this.genreRepo  = genreRepo;
        this.conGenRepo = conGenRepo;
    }

    public List<Product> findAllProducts() {
        return prodRepo.findAll();
    }

    public Optional<Product> findProductById(long Id) {return prodRepo.findById(Id);}

}
