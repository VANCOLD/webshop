package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.enums.EsrbRating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "price")
    private Double price;

    // Placeholder for now
    @Column(name = "image")
    private String image;

    @Column(name = "tax")
    private Byte tax;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "gtin")
    private String gtin;

    @Column(name = "available")
    private LocalDateTime available;

    @Enumerated(EnumType.STRING)
    @Column(name = "esrb_rating")
    private EsrbRating esrbRating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "console_generation_id", nullable=false)
    private ConsoleGeneration consoleGeneration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="producer_id", nullable=false)
    private Producer producer;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "product_to_genre",
        joinColumns = { @JoinColumn(name = "product_id") },
        inverseJoinColumns = { @JoinColumn(name = "genre_id") }
    )
    private List<Genre> genreList;

}
