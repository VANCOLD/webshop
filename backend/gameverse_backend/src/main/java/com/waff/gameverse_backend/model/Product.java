package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.*;
import com.waff.gameverse_backend.enums.EsrbRating;
import com.waff.gameverse_backend.utils.DataTransferObject;
import com.waff.gameverse_backend.utils.SimpleDataTransferObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@code Product} class represents a product in a game store, which can be a video game, console, or related item.
 * It contains various attributes and associations to categorize and manage products.
 *
 * <p>This class is an entity that can be persisted to a database using JPA (Java Persistence API).
 * It is also annotated with Lombok annotations to generate getters, setters, and constructors automatically.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product implements DataTransferObject<ProductDto>, SimpleDataTransferObject<SimpleProductDto> {

    /**
     * The unique identifier for this product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the product.
     */
    @Column(name="name")
    private String name;

    /**
     * A description of the product.
     */
    @Column(name="description")
    private String description;

    /**
     * The price of the product.
     */
    @Column(name = "price")
    private Double price;

    /**
     * A placeholder for the product's image (file path or URL).
     */
    @Column(name = "image")
    private String image;

    /**
     * The tax rate applicable to the product.
     */
    @Column(name = "tax")
    private Integer tax;

    /**
     * The quantity of the product in stock.
     */
    @Column(name = "stock")
    private Integer stock;

    /**
     * The Global Trade Item Number (GTIN) of the product.
     */
    @Column(name = "gtin")
    private String gtin;

    /**
     * The date and time when the product is available for purchase.
     */
    @Column(name = "available")
    private LocalDateTime available;

    /**
     * The Entertainment Software Rating Board (ESRB) rating of the product.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "esrb_rating")
    private EsrbRating esrbRating;

    /**
     * The console generation associated with this product.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "console_generation_id")
    private ConsoleGeneration consoleGeneration;

    /**
     * The category to which this product belongs.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * The producer or manufacturer of the product.
     */
    @ManyToOne
    @JoinColumn(name="producer_id")
    private Producer producer;

    /**
     * The list of genres associated with this product.
     * Each genre in the list categorizes this product.
     */
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "products_to_genres",
        joinColumns = { @JoinColumn(name = "product_id") },
        inverseJoinColumns = { @JoinColumn(name = "genre_id") }
    )
    private List<Genre> genres;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "products_to_carts",
        joinColumns = { @JoinColumn(name = "product_id") },
        inverseJoinColumns = { @JoinColumn(name = "cart_id") }
    )
    private List<Cart> carts;

    public Product(SimpleProductDto productDto) {
        this.id = productDto.getId();
        this.name = productDto.getName();
        this.description = productDto.getDescription();
        this.image = productDto.getImage();
        this.tax   = productDto.getTax();
    }

    @Override
    public ProductDto convertToDto() {

        ProductDto productDto = new ProductDto();

        productDto.setId(id);
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setImage(image);
        productDto.setTax(tax);
        productDto.setStock(stock);
        productDto.setGtin(gtin);
        productDto.setAvailable(available);
        productDto.setEsrbRating(esrbRating.getName());
        productDto.setConsoleGeneration(consoleGeneration == null ? new ConsoleGenerationDto() : consoleGeneration.convertToDto());
        productDto.setCategory(category == null ? new CategoryDto() : category.convertToDto());
        productDto.setProducer(producer == null ? new ProducerDto() : producer.convertToDto());
        productDto.setGenres(genres.stream().map(Genre::convertToDto).toList());

        return productDto;
    }

    @Override
    public SimpleProductDto convertToSimpleDto() {

        SimpleProductDto simpleProductDto = new SimpleProductDto();

        simpleProductDto.setName(name);
        simpleProductDto.setDescription(description);
        simpleProductDto.setPrice(price);
        simpleProductDto.setImage(image);
        simpleProductDto.setTax(tax);
        simpleProductDto.setStock(stock);
        simpleProductDto.setGtin(gtin);

        return simpleProductDto;
    }
}