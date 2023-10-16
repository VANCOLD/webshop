package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.AddressDto;
import com.waff.gameverse_backend.dto.ProducerDto;
import com.waff.gameverse_backend.utils.DataTransferObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
@Table(name = "producers")
public class Producer implements DataTransferObject<ProducerDto> {

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
    private List<Product> products = new ArrayList<>();;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;


    public Producer(ProducerDto producer) {
        this.id = producer.getId();
        this.name = producer.getName();
        this.address = new Address(producer.getAddress());
    }


    @Override
    public ProducerDto convertToDto() {
        return new ProducerDto(id, name, address == null ? new AddressDto() : address.convertToDto());
    }
}
