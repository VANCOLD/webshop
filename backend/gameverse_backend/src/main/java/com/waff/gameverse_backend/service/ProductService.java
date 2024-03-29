package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.ProductDto;
import com.waff.gameverse_backend.enums.EsrbRating;
import com.waff.gameverse_backend.model.Category;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final ProducerService producerService;

    private final ConsoleGenerationService consoleGenerationService;


    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService,
                          ProducerService producerService,
                          ConsoleGenerationService consoleGenerationService,
                          GenreService genreService) {

        this.productRepository          = productRepository;
        this.categoryService            = categoryService;
        this.producerService            = producerService;
        this.consoleGenerationService   = consoleGenerationService;
    }

    /**
     * Find a product by its ID.
     *
     * @param id The ID of the product to find.
     * @return The found product.
     * @throws NoSuchElementException If no product with the given ID exists.
     */
    public Product findById(Long id) {
        return this.productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Product with the given ID does not exist"));
    }

    /**
     * Find all products.
     *
     * @return A list of all products.
     */
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    /**
     * Find a product by its name.
     *
     * @param name The name of the product to find.
     * @return The found product.
     * @throws NoSuchElementException If no product with the given name exists.
     */
    public Product findByName(String name) {
        return this.productRepository.findByName(name)
            .orElseThrow(() -> new NoSuchElementException("Product with the given name does not exist"));
    }


    /**
     * Save a new product with the given name.
     *
     * @param product The new product to save (simple form).
     * @return The saved product.
     * @throws IllegalArgumentException If a product with the same name already exists.
     */
    public Product save(ProductDto product) {

        var toCheck = this.productRepository.findByName(product.getName());

        if (toCheck.isEmpty()) {
            Product toSave = new Product(product);

            if(categoryService.exists(product.getCategory())) {
                var category = categoryService.findByName(product.getCategory().getName());
                toSave.setCategory(category);
            } else {
                throw new NoSuchElementException("Die angegebene Kategorie exisiert nicht!");
            }

            if(producerService.exists(product.getProducer())) {
                var producer = producerService.findByName(product.getProducer().getName());
                toSave.setProducer(producer);
            } else {
                throw new NoSuchElementException("Der angegebene Producer exisiert nicht!");
            }

            if(!product.getConsoleGeneration().getName().equals("None")) {
                if(consoleGenerationService.exists(product.getConsoleGeneration())) {
                    var consoleGeneration = consoleGenerationService.findByName(product.getConsoleGeneration().getName());
                    toSave.setConsoleGeneration(consoleGeneration);
                } else {
                    throw new NoSuchElementException("Die angegebene Konsolengeneration exisiert nicht!");
                }
            }

            return this.productRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a product");
        }
    }

    /**
     * Update a product with the information from the provided ProductDto.
     *
     * @param product The ProductDto containing updated product information.
     * @return The updated product.
     * @throws NoSuchElementException  If the product with the given ID does not exist.
     * @throws IllegalArgumentException If the product name is empty.
     */
    public Product update(ProductDto product) {
            var toUpdate = this.productRepository.findById(product.getId())
            .orElseThrow(() -> new NoSuchElementException("Product with the given ID does not exist"));

        if (product.getName().isEmpty()) {
            throw new IllegalArgumentException("The name of the product cannot be empty");
        }

        toUpdate.setName(product.getName());
        toUpdate.setDescription(product.getDescription());
        toUpdate.setPrice(product.getPrice());
        toUpdate.setTax(product.getTax());
        toUpdate.setStock(product.getStock());
        toUpdate.setGtin(product.getGtin());
        toUpdate.setAvailable(product.getAvailable());
        toUpdate.setImage(product.getImage());
        toUpdate.setEsrbRating(EsrbRating.getEsrbRating(product.getEsrbRating()));

        if(categoryService.exists(product.getCategory())) {
            var category = categoryService.findByName(product.getCategory().getName());
            toUpdate.setCategory(category);
        } else {
            throw new NoSuchElementException("Die angegebene Kategorie exisiert nicht!");
        }

        if(producerService.exists(product.getProducer())) {
            var producer = producerService.findByName(product.getProducer().getName());
            toUpdate.setProducer(producer);
        } else {
            throw new NoSuchElementException("Der angegebene Producer exisiert nicht!");
        }

        if(!product.getConsoleGeneration().getName().equals("None")) {
            if(consoleGenerationService.exists(product.getConsoleGeneration())) {
                var consoleGeneration = consoleGenerationService.findByName(product.getConsoleGeneration().getName());
                toUpdate.setConsoleGeneration(consoleGeneration);
            } else {
                throw new NoSuchElementException("Die angegebene Konsolengeneration exisiert nicht!");
            }
        }

        return this.productRepository.save(toUpdate);
    }

    /**
     * Delete a product with the given ID and update associated roles.
     *
     * @param id The ID of the product to delete.
     * @return The deleted product.
     * @throws NoSuchElementException If the product with the given ID does not exist.
     */
    public Product delete(Long id) {

        var toDelete = this.productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Product with the given ID does not exist"));

        toDelete.setGenres(new ArrayList<>());
        toDelete.getCategory().setProducts(null);
        toDelete.setCategory(null);
        toDelete.setProducer(null);
        toDelete.setConsoleGeneration(null);

        this.productRepository.delete(toDelete);
        return toDelete;
    }

    /**
     * Retrieve all products associated with a specific category by category ID.
     *
     * @param categoryId The ID of the category to search for.
     * @return A list of products containing the specified category.
     * @throws NoSuchElementException If the category with the given ID does not exist.
     */
    public List<Product> findAllProductsByCategory(Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return productRepository.findAllByCategory(category);
    }
}