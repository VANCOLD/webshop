package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;

@Entity
@Table(name = "ConsoleGeneration")
public class ConsoleGeneration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cg_id")
    private Long cg_id;

    @Column(name = "name")
    private String name;

    public Long getCg_id() {
        return cg_id;
    }

    public String getName() {
        return name;
    }
}
