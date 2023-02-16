package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Position")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Position
{
    @Id
    @GeneratedValue
    @Column(name="positionId")
    private Long positionId;


    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    @Column(name="product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    @Column(name="cart")
    private Cart cart;

}
