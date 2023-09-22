package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.ConsoleGenerationDto;
import com.waff.gameverse_backend.model.ConsoleGeneration;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.repository.ConsoleGenerationRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

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
     * Find a consoleGeneratio by its ID.
     *
     * @param id The ID of the consoleGeneratio to find.
     * @return The found consoleGeneratio.
     * @throws NoSuchElementException If no consoleGeneratio with the given ID exists.
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
     * Find a consoleGeneratio by its name.
     *
     * @param name The name of the consoleGeneratio to find.
     * @return The found consoleGeneratio.
     * @throws NoSuchElementException If no consoleGeneratio with the given name exists.
     */
    public ConsoleGeneration findByName(String name) {
        return this.consoleGenerationRepository.findByName(name)
            .orElseThrow(() -> new NoSuchElementException("ConsoleGeneration with the given name does not exist"));
    }

    /**
     * Save a new consoleGeneratio with the given name.
     *
     * @param name The name of the new consoleGeneratio to save.
     * @return The saved consoleGeneratio.
     * @throws IllegalArgumentException If a consoleGeneratio with the same name already exists.
     */
    public ConsoleGeneration save(String name) {
        var toCheck = this.consoleGenerationRepository.findByName(name);
        if (toCheck.isEmpty()) {
            ConsoleGeneration toSave = new ConsoleGeneration();
            toSave.setName(name);
            return this.consoleGenerationRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a consoleGeneratio");
        }
    }

    /**
     * Update a consoleGeneratio with the information from the provided ConsoleGenerationDto.
     *
     * @param consoleGeneratioDto The ConsoleGenerationDto containing updated consoleGeneratio information.
     * @return The updated consoleGeneratio.
     * @throws NoSuchElementException  If the consoleGeneratio with the given ID does not exist.
     * @throws IllegalArgumentException If the consoleGeneratio name is empty.
     */
    public ConsoleGeneration update(ConsoleGenerationDto consoleGeneratioDto) {
        var toUpdate = this.consoleGenerationRepository.findById(consoleGeneratioDto.getId())
            .orElseThrow(() -> new NoSuchElementException("ConsoleGeneration with the given ID does not exist"));
        if (consoleGeneratioDto.getName().isEmpty()) {
            throw new IllegalArgumentException("The name of the consoleGeneratio cannot be empty");
        }
        toUpdate.setName(consoleGeneratioDto.getName());
        return this.consoleGenerationRepository.save(toUpdate);
    }

    /**
     * Delete a consoleGeneratio with the given ID and update associated roles.
     *
     * @param id The ID of the consoleGeneratio to delete.
     * @return The deleted consoleGeneratio.
     * @throws NoSuchElementException If the consoleGeneratio with the given ID does not exist.
     */
    public ConsoleGeneration delete(Long id) {
        var toDelete = this.consoleGenerationRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("ConsoleGeneration with the given ID does not exist"));
        var products = this.productRepository.findAllByConsoleGeneration(toDelete);
        for (Product product : products) {
            product.setConsoleGeneration(null);
            this.productRepository.save(product);
        }
        toDelete.setProduct(null);
        this.consoleGenerationRepository.save(toDelete);
        this.consoleGenerationRepository.delete(toDelete);
        return toDelete;
    }
}
