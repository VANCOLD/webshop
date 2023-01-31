package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;

@Entity
@Table(name = "Genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gid")
    private Long gid;

    @Column(name = "name")
    private String name;

    public Long getGid() {
        return gid;
    }

    public String getName() {
        return name;
    }
}
