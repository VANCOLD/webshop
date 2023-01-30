package com.waff.gameverse_backend.Datenmodell;

import jakarta.persistence.*;

@Entity
@Table(name = "Genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gid")
    private Long gid;

    @Column(name = "description")
    private String description;

    public Long getGid() {
        return gid;
    }

    public String getDescription() {
        return description;
    }
}
