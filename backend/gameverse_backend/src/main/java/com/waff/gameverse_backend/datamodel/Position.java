package com.waff.gameverse_backend.datamodel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="position")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Position{
    @Id
    @Column(name="positionId")
    private Long positionId;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;

    @Column(name="quantity")
    private Integer quantity;
}
