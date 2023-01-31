package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;

@Entity
public class Producer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "h_id")
    private Long h_id;

    @Column(name = "name")
    private String name;

}
