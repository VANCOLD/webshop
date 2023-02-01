package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name="game") @Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Game extends Product
{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cg_id", referencedColumnName = "cg_id")
    private ConsoleGeneration consoleGen;

    @Column(name = "esrb")
    private String  esrb;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "genres",
            joinColumns = { @JoinColumn(name = "pid") },
            inverseJoinColumns = { @JoinColumn(name = "gid") })
    private Set<Genre> genres = new HashSet<>();

}
