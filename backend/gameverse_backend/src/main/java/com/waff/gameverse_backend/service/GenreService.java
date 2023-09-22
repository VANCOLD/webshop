package com.waff.gameverse_backend.service;

    import com.waff.gameverse_backend.dto.GenreDto;
    import com.waff.gameverse_backend.model.Genre;
    import com.waff.gameverse_backend.model.Product;
    import com.waff.gameverse_backend.repository.GenreRepository;
    import com.waff.gameverse_backend.repository.ProductRepository;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.NoSuchElementException;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    private final ProductRepository productRepository;

    public GenreService(GenreRepository genreRepository, ProductRepository productRepository) {
        this.genreRepository = genreRepository;
        this.productRepository           = productRepository;
    }

    /**
     * Find a genre by its ID.
     *
     * @param id The ID of the genre to find.
     * @return The found genre.
     * @throws NoSuchElementException If no genre with the given ID exists.
     */
    public Genre findById(Long id) {
        return this.genreRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Genre with the given ID does not exist"));
    }

    /**
     * Find all genres.
     *
     * @return A list of all genres.
     */
    public List<Genre> findAll() {
        return this.genreRepository.findAll();
    }

    /**
     * Find a genre by its name.
     *
     * @param name The name of the genre to find.
     * @return The found genre.
     * @throws NoSuchElementException If no genre with the given name exists.
     */
    public Genre findByName(String name) {
        return this.genreRepository.findByName(name)
            .orElseThrow(() -> new NoSuchElementException("Genre with the given name does not exist"));
    }

    /**
     * Save a new genre with the given name.
     *
     * @param name The name of the new genre to save.
     * @return The saved genre.
     * @throws IllegalArgumentException If a genre with the same name already exists.
     */
    public Genre save(String name) {
        var toCheck = this.genreRepository.findByName(name);
        if (toCheck.isEmpty()) {
            Genre toSave = new Genre();
            toSave.setName(name);
            return this.genreRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a genre");
        }
    }

    /**
     * Update a genre with the information from the provided GenreDto.
     *
     * @param genreDto The GenreDto containing updated genre information.
     * @return The updated genre.
     * @throws NoSuchElementException  If the genre with the given ID does not exist.
     * @throws IllegalArgumentException If the genre name is empty.
     */
    public Genre update(GenreDto genreDto) {
        var toUpdate = this.genreRepository.findById(genreDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Genre with the given ID does not exist"));
        if (genreDto.getName().isEmpty()) {
            throw new IllegalArgumentException("The name of the genre cannot be empty");
        }
        toUpdate.setName(genreDto.getName());
        return this.genreRepository.save(toUpdate);
    }

    /**
     * Delete a genre with the given ID and update associated roles.
     *
     * @param id The ID of the genre to delete.
     * @return The deleted genre.
     * @throws NoSuchElementException If the genre with the given ID does not exist.
     */
    public Genre delete(Long id) {
        var toDelete = this.genreRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Genre with the given ID does not exist"));
        var products = this.productRepository.findAllByGenre(toDelete);
        for (Product product : products) {
            product.setGenreList(new ArrayList<>());
            this.productRepository.save(product);
        }
        toDelete.setProductList(new ArrayList<>());
        this.genreRepository.save(toDelete);
        this.genreRepository.delete(toDelete);
        return toDelete;
    }
}
