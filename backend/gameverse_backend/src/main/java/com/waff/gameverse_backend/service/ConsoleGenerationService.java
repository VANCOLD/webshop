package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.ConsoleGenerationDto;
import com.waff.gameverse_backend.model.ConsoleGeneration;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.repository.ConsoleGenerationRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ConsoleGenerationService {
    private final ConsoleGenerationRepository consoleGenerationRepository;

    private final ProductRepository productRepository;

    public ConsoleGenerationService(ConsoleGenerationRepository consoleGenerationRepository, ProductRepository productRepository) {
        this.consoleGenerationRepository = consoleGenerationRepository;
        this.productRepository           = productRepository;
    }

    /**
     * Find a console generation by its ID.
     *
     * @param id The ID of the console generation to find.
     * @return The found console generation.
     * @throws NoSuchElementException If no console generation with the given ID exists.
     */
    public ConsoleGeneration findById(Long id) {
        return this.consoleGenerationRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("ConsoleGeneration with the given ID does not exist"));
    }

    /**
     * Find all consoleGeneratios.
     *
     * @return A list of all consoleGeneratios.
     */
    public List<ConsoleGeneration> findAll() {
        return this.consoleGenerationRepository.findAll();
    }

    /**
     * Find a console generation by its name.
     *
     * @param name The name of the console generation to find.
     * @return The found console generation.
     * @throws NoSuchElementException If no console generation with the given name exists.
     */
    public ConsoleGeneration findByName(String name) {
        return this.consoleGenerationRepository.findByName(name)
            .orElseThrow(() -> new NoSuchElementException("ConsoleGeneration with the given name does not exist"));
    }

    /**
     * Save a new console generation with the given name.
     *
     * @param name The name of the new console generation to save.
     * @return The saved console generation.
     * @throws IllegalArgumentException If a console generation with the same name already exists.
     */
    public ConsoleGeneration save(String name) {
        var toCheck = this.consoleGenerationRepository.findByName(name);
        if (toCheck.isEmpty()) {
            ConsoleGeneration toSave = new ConsoleGeneration();
            toSave.setName(name);
            return this.consoleGenerationRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a console generation");
        }
    }

    /**
     * Update a console generation with the information from the provided ConsoleGenerationDto.
     *
     * @param consoleGeneratioDto The ConsoleGenerationDto containing updated console generation information.
     * @return The updated console generation.
     * @throws NoSuchElementException  If the console generation with the given ID does not exist.
     * @throws IllegalArgumentException If the console generation name is empty.
     */
    public ConsoleGeneration update(ConsoleGenerationDto consoleGeneratioDto) {
        var toUpdate = this.consoleGenerationRepository.findById(consoleGeneratioDto.getId())
            .orElseThrow(() -> new NoSuchElementException("ConsoleGeneration with the given ID does not exist"));
        if (consoleGeneratioDto.getName().isEmpty()) {
            throw new IllegalArgumentException("The name of the console generation cannot be empty");
        }
        toUpdate.setName(consoleGeneratioDto.getName());
        return this.consoleGenerationRepository.save(toUpdate);
    }

    /**
     * Delete a console generation with the given ID and update associated roles.
     *
     * @param id The ID of the console generation to delete.
     * @return The deleted console generation.
     * @throws NoSuchElementException If the console generation with the given ID does not exist.
     */
    public ConsoleGeneration delete(Long id) {
        var toDelete = this.consoleGenerationRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("ConsoleGeneration with the given ID does not exist"));
        var products = this.productRepository.findAllByConsoleGeneration(toDelete);
        for (Product product : products) {
            product.setConsoleGeneration(null);
            this.productRepository.save(product);
        }
        toDelete.setProducts(new ArrayList<>());
        this.consoleGenerationRepository.delete(toDelete);
        return toDelete;
    }
}
