package com.waff.gameverse_backend.datamodel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Table(name = "product")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Product
{

    @Id
    @Column(name = "pid")
    private Long pid;

    @Column(name = "name")
    private String      name;

    @Column(name = "description")
    private String      description;

    @Column(name = "price")
    private Float       price;

    @Column(name = "image")
    private String      image;

    @Column(name = "tax")
    private Integer     tax;

    @Column(name = "internal_pid")
    private Long        hpid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proid", referencedColumnName = "proid")
    private Producer producer;

    @Column(name = "amount")
    private Integer     amount;

    @Column(name = "available")
    private LocalDateTime available;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name="cid", referencedColumnName = "cid")
    private Category category;

}
