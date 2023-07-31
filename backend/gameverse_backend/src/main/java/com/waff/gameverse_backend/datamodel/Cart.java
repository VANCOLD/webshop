package com.waff.gameverse_backend.datamodel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.waff.gameverse_backend.enums.Status;
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
    @Column(name="cartId")
    private Long cartId;

    @OneToMany(mappedBy = "cart")
    @JsonBackReference
    @Column(name="productList")
    private Set<Position> productList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

}
