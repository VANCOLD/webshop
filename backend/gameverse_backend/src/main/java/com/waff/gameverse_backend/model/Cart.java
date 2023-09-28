package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.CartDto;
import com.waff.gameverse_backend.utils.DataTransferObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart implements DataTransferObject<CartDto> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "products_to_carts",
        joinColumns = { @JoinColumn(name = "cart_id") },
        inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> products;

    @Override
    public CartDto convertToDto() {
        return new CartDto(id, user.convertToDto(), products.stream().map(Product::convertToDto).toList());
    }
}
