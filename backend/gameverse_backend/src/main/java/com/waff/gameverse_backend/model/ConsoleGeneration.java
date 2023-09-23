package com.waff.gameverse_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The {@code ConsoleGeneration} class represents a generation of game consoles.
 * It is used to categorize and group game consoles by their respective generations.
 *
 * <p>This class is an entity that can be persisted to a database using JPA (Java Persistence API).
 * It is also annotated with Lombok annotations to generate getters, setters, and constructors automatically.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "console_generation")
public class ConsoleGeneration {

    /**
     * The unique identifier for this console generation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the console generation.
     */
    @Column(name = "name")
    private String name;

    /**
     * The list of products associated with this console generation.
     * Each product in the list belongs to this specific console generation.
     */
    @OneToMany(mappedBy = "consoleGeneration")
    private List<Product> productList;
}
