package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.OrderDto;
import com.waff.gameverse_backend.enums.OrderStatus;
import com.waff.gameverse_backend.utils.DataTransferObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "orders")
public class Order implements DataTransferObject<OrderDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy="order")
    private List<OrderedProduct> orderedProducts;


    public Order(OrderDto order) {
        this.id = order.getId();
        this.user = new User(order.getUser());
        this.orderedProducts = order.getOrderedProducts().stream().map(OrderedProduct::new).toList();
        this.orderStatus = OrderStatus.valueOf(order.getOrderStatus().toUpperCase());
    }


    @Override
    public OrderDto convertToDto() {
        return new OrderDto(id, user.convertToDto(), orderStatus.getName(), orderedProducts.stream().map(OrderedProduct::convertToDto).toList());
    }
}
