package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.CategoryDto;
import com.waff.gameverse_backend.model.Category;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.repository.CategoryRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository  = productRepository;
    }

    /**
     * Find a category by its ID.
     *
     * @param id The ID of the category to find.
     * @return The found category.
     * @throws NoSuchElementException If no category with the given ID exists.
     */
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Category with the given ID does not exist"));
    }

    /**
     * Find all categorys.
     *
     * @return A list of all categorys.
     */
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    /**
     * Find a category by its name.
     *
     * @param name The name of the category to find.
     * @return The found category.
     * @throws NoSuchElementException If no category with the given name exists.
     */
    public Category findByName(String name) {
        return this.categoryRepository.findByName(name)
            .orElseThrow(() -> new NoSuchElementException("Category with the given name does not exist"));
    }

    /**
     * Save a new category with the given name.
     *
     * @param name The name of the new category to save.
     * @return The saved category.
     * @throws IllegalArgumentException If a category with the same name already exists.
     */
    public Category save(String name) {
        var toCheck = this.categoryRepository.findByName(name);
        if (toCheck.isEmpty()) {
            Category toSave = new Category();
            toSave.setName(name);
            return this.categoryRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a category");
        }
    }

    /**
     * Update a category with the information from the provided CategoryDto.
     *
     * @param categoryDto The CategoryDto containing updated category information.
     * @return The updated category.
     * @throws NoSuchElementException  If the category with the given ID does not exist.
     * @throws IllegalArgumentException If the category name is empty.
     */
    public Category update(CategoryDto categoryDto) {
        var toUpdate = this.categoryRepository.findById(categoryDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Category with the given ID does not exist"));
        if (categoryDto.getName().isEmpty()) {
            throw new IllegalArgumentException("The name of the category cannot be empty");
        }
        toUpdate.setName(categoryDto.getName());
        return this.categoryRepository.save(toUpdate);
    }

    /**
     * Delete a category with the given ID and update associated roles.
     *
     * @param id The ID of the category to delete.
     * @return The deleted category.
     * @throws NoSuchElementException If the category with the given ID does not exist.
     */
    public Category delete(Long id) {

        var toDelete = this.categoryRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Category with the given ID does not exist"));
        var products = this.productRepository.findAllByCategory(toDelete);

        for (Product product : products) {
            product.setCategory(null);
            this.productRepository.save(product);
        }

        toDelete.setProduct(null);
        this.categoryRepository.save(toDelete);
        this.categoryRepository.delete(toDelete);
        return toDelete;
    }
}
