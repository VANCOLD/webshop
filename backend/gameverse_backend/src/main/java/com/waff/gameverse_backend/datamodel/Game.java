package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name="game")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Game extends Product
{

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cg_id", referencedColumnName = "cg_id")
    private ConsoleGeneration consoleGen;


    @NotBlank @NotNull @NotEmpty
    @Column(name = "esrb")
    private String  esrb;


    @NotNull
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
