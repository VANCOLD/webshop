package com.waff.gameverse_backend.Datenmodell;


import jakarta.persistence.*;
import java.net.URL;
import java.util.Date;


@Entity
@Table(name = "Product")
public class Product
{


    //////////////////////////////////////////////////////////////////////
    ////////////         Instance Variables                   ////////////
    //////////////////////////////////////////////////////////////////////

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
    private URL         image;

    @Column(name = "tax")
    private Integer     tax;

    @Column(name = "internal_pid")
    private Long        hpid;

    @Column(name = "amount")
    private Integer     amount;

    @Column(name = "available")
    private Date        available;


    //////////////////////////////////////////////////////////////////////
    ////////////              Constructors                    ////////////
    //////////////////////////////////////////////////////////////////////

    public Product( Long pid, String name, String description,
                   Float price, URL image, Integer tax,
                   Long hpid, Integer amount, Date available )
    {
        this.pid            = pid;
        this.name           = name;
        this.description    = description;
        this.price          = price;
        this.image          = image;
        this.tax            = tax;
        this.hpid           = hpid;
        this.amount         = amount;
        this.available      = available;
    }

    public Product() {}


    //////////////////////////////////////////////////////////////////////
    ////////////              Getter Methods                  ////////////
    //////////////////////////////////////////////////////////////////////

    public Long getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public URL getImage() {
        return image;
    }

    public Integer getTax() {
        return tax;
    }

    public Long getHpid() {
        return hpid;
    }

    public Integer getAmount() {
        return amount;
    }

    public Date getAvailable() {
        return available;
    }
}
