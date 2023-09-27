package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.ProducerDto;
import com.waff.gameverse_backend.model.Producer;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.repository.ProducerRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProducerService {
    private final ProducerRepository producerRepository;

    private final ProductRepository productRepository;

    public ProducerService(ProducerRepository producerRepository, ProductRepository productRepository) {
        this.producerRepository = producerRepository;
        this.productRepository           = productRepository;
    }

    /**
     * Find a producer by its ID.
     *
     * @param id The ID of the producer to find.
     * @return The found producer.
     * @throws NoSuchElementException If no producer with the given ID exists.
     */
    public Producer findById(Long id) {
        return this.producerRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Producer with the given ID does not exist"));
    }

    /**
     * Find all producers.
     *
     * @return A list of all producers.
     */
    public List<Producer> findAll() {
        return this.producerRepository.findAll();
    }

    /**
     * Find a producer by its name.
     *
     * @param name The name of the producer to find.
     * @return The found producer.
     * @throws NoSuchElementException If no producer with the given name exists.
     */
    public Producer findByName(String name) {
        return this.producerRepository.findByName(name)
            .orElseThrow(() -> new NoSuchElementException("Producer with the given name does not exist"));
    }

    /**
     * Save a new producer with the given name.
     *
     * @param name The name of the new producer to save.
     * @return The saved producer.
     * @throws IllegalArgumentException If a producer with the same name already exists.
     */
    public Producer save(String name) {
        var toCheck = this.producerRepository.findByName(name);
        if (toCheck.isEmpty()) {
            Producer toSave = new Producer();
            toSave.setName(name);
            return this.producerRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a producer");
        }
    }

    /**
     * Update a producer with the information from the provided ProducerDto.
     *
     * @param producerDto The ProducerDto containing updated producer information.
     * @return The updated producer.
     * @throws NoSuchElementException  If the producer with the given ID does not exist.
     * @throws IllegalArgumentException If the producer name is empty.
     */
    public Producer update(ProducerDto producerDto) {
        var toUpdate = this.producerRepository.findById(producerDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Producer with the given ID does not exist"));
        if (producerDto.getName().isEmpty()) {
            throw new IllegalArgumentException("The name of the producer cannot be empty");
        }
        toUpdate.setName(producerDto.getName());
        return this.producerRepository.save(toUpdate);
    }

    /**
     * Delete a producer with the given ID and update associated roles.
     *
     * @param id The ID of the producer to delete.
     * @return The deleted producer.
     * @throws NoSuchElementException If the producer with the given ID does not exist.
     */
    public Producer delete(Long id) {
        var toDelete = this.producerRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Producer with the given ID does not exist"));
        var products = this.productRepository.findAllByProducer(toDelete);
        for (Product product : products) {
            product.setProducer(null);
            this.productRepository.save(product);
        }
        toDelete.setProducts(new ArrayList<>());
        this.producerRepository.delete(toDelete);
        return toDelete;
    }
}
