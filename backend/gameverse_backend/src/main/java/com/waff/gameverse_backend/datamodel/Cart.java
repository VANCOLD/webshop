package com.waff.gameverse_backend.datamodel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "cart")
    @JsonBackReference
    @Column(name="productList")
    private Set<Position> productList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @Column(name="user")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sid", referencedColumnName = "sid")
    @Column(name="status")
    private Status status;
}
