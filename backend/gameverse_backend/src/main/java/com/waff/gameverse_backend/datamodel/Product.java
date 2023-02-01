package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity @Table(name = "Product")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Product
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "h_id", referencedColumnName = "h_id")
    private Producer producer;

    @Column(name = "internal_pid")
    private Long        hpid;

    @Column(name = "amount")
    private Integer     amount;

    @Column(name = "available")
    private Date        available;

}
