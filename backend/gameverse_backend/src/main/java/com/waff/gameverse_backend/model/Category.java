package com.waff.gameverse_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The {@code Category} class represents a category for products in a game store.
 * It is used to group similar products together.
 *
 * <p>This class is an entity that can be persisted to a database using JPA (Java Persistence API).
 * It is also annotated with Lombok annotations to generate getters, setters, and constructors automatically.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    /**
     * The unique identifier for this category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the category.
     */
    @Column(name = "name")
    private String name;

    /**
     * The product associated with this category.
     */
    @OneToMany(mappedBy="category")
    private List<Product> products;
}
