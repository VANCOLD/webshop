package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "ConsoleGeneration")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class ConsoleGeneration
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cg_id")
    private Long cg_id;

    @Column(name = "name")
    private String name;

}
