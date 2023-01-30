package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;

import java.net.URL;
import java.util.Date;

@Entity
public class Game extends Product
{

    @OneToOne(cascade = CascadeType.ALL)
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

    public Game(Long pid, String name, String description,
                Float price, String image, Integer tax,
                Long hpid, Integer amount, Date available,
                String esrb)
    {

        super(pid, name, description, price, image, tax, hpid, amount, available);
        this.esrb = esrb;

    }
}
