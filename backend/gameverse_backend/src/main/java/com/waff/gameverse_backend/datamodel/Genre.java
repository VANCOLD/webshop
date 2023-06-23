package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity @Table(name = "Genre")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Genre
{

    @Id
    @Column(name = "gid")
    private Long gid;


    @Column(name = "name")
    private String name;

    @ManyToMany
    private Set<Product> product;

}
