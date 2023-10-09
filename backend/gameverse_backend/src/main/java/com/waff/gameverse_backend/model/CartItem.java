package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.CartItemDto;
import com.waff.gameverse_backend.utils.DataTransferObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_to_carts")
@Entity
public class CartItem implements DataTransferObject<CartItemDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartItem(CartItemDto cartItems) {
        this.id         = cartItems.getId();
        this.amount     = cartItems.getAmount();
        this.product    = new Product(cartItems.getProduct());
        this.cart       = new Cart(cartItems.getCart());
    }

    @Override
    public CartItemDto convertToDto() {
        return new CartItemDto(id, amount, product.convertToDto(), cart.convertToDto());
    }
}
