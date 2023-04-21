package com.waff.gameverse_backend.datamodel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @Column(name="positionId")
    private Long positionId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;

    @Min(1)
    @Column(name="quantity")
    private Integer quantity;
}
