package com.waff.gameverse_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "product_to_genre",
        joinColumns = { @JoinColumn(name = "genre_id") },
        inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> productList;
}
