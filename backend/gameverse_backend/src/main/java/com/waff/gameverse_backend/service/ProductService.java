package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.ProductDto;
import com.waff.gameverse_backend.dto.SimpleProductDto;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public Product save(SimpleProductDto product) {
        var toCheck = this.productRepository.findByName(product.getName());
        if (toCheck.isEmpty()) {
            Product toSave = new Product(product);
            return this.productRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a product");
        }
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
            return this.productRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a product");
        }
    }

    /**
     * Update a product with the information from the provided ProductDto.
     *
     * @param productDto The ProductDto containing updated product information.
     * @return The updated product.
     * @throws NoSuchElementException  If the product with the given ID does not exist.
     * @throws IllegalArgumentException If the product name is empty.
     */
    public Product update(ProductDto productDto) {
        var toUpdate = this.productRepository.findById(productDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Product with the given ID does not exist"));
        if (productDto.getName().isEmpty()) {
            throw new IllegalArgumentException("The name of the product cannot be empty");
        }
        toUpdate.setName(productDto.getName());
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
}
