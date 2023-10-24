package com.waff.gameverse_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.waff.gameverse_backend.dto.GenreDto;
import com.waff.gameverse_backend.utils.DataTransferObject;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Genre} class represents a genre of products, typically used in the context of video games or other media.
 * It is used to categorize and group similar products together based on their genre.
 *
 * <p>This class is an entity that can be persisted to a database using JPA (Java Persistence API).
 * It is also annotated with Lombok annotations to generate getters, setters, and constructors automatically.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class Genre implements DataTransferObject<GenreDto> {

    /**
     * The unique identifier for this genre.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the genre.
     */
    @Column(name = "name")
    private String name;

    /**
     * The list of products associated with this genre.
     * Each product in the list belongs to this specific genre.
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "products_to_genres",
        joinColumns = { @JoinColumn(name = "genre_id") },
        inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> products = new ArrayList<>();

    public Genre(GenreDto genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    @Override
    public GenreDto convertToDto() {
        return new GenreDto(id, name);
    }
}
