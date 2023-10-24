package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.OrderedProductDto;
import com.waff.gameverse_backend.utils.DataTransferObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordered_products")
public class OrderedProduct implements DataTransferObject<OrderedProductDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "tax")
    private Integer tax;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Product product;

    public OrderedProduct(OrderedProductDto orderedProducts) {
        this.id = orderedProducts.getId();
        this.amount = orderedProducts.getAmount();
        this.description = orderedProducts.getDescription();
        this.tax = orderedProducts.getTax();
        this.name = orderedProducts.getName();
        this.price = orderedProducts.getPrice();
    }

    @Override
    public OrderedProductDto convertToDto() {
        return new OrderedProductDto(id, name, description, price, tax, amount);
    }
}
