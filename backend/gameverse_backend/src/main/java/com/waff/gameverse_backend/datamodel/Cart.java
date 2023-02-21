package com.waff.gameverse_backend.datamodel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="Cart")
@Setter @Getter @NoArgsConstructor@AllArgsConstructor
public class Cart
{

    @Id
    @GeneratedValue
    @Column(name="cartId")
    private Long cartId;

    @NotNull
    @OneToMany(mappedBy = "cart")
    @JsonBackReference
    @Column(name="productList")
    private Set<Position> productList;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sid", referencedColumnName = "sid")
    private Status status;
}
