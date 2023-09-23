package com.waff.gameverse_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The {@code Producer} class represents a producer or manufacturer of products.
 * It is used to associate products with their respective producers or manufacturers.
 *
 * <p>This class is an entity that can be persisted to a database using JPA (Java Persistence API).
 * It is also annotated with Lombok annotations to generate getters, setters, and constructors automatically.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producer")
public class Producer {

    /**
     * The unique identifier for this producer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the producer or manufacturer.
     */
    @Column(name = "name")
    private String name;

    /**
     * The list of products associated with this producer.
     * Each product in the list is produced or manufactured by this specific producer.
     */
    @OneToMany(mappedBy="producer")
    private List<Product> productList;
}
