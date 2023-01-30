package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;

@Entity
@Table(name = "Game")
public class Game extends Product
{

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "cg_id", referencedColumnName = "cg_id")
    private ConsoleGeneration consoleGen;

    @Column(name = "esrb")
    private String  esrb;


    /*
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tutorial_tags",
            joinColumns = { @JoinColumn(name = "pid") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
            private Set<Genre> genres = new HashSet<>();
*/

    public Game() {
        this("");
    }

    public Game(String esrb)
    {
        super();
        this.esrb = esrb;
    }
}
