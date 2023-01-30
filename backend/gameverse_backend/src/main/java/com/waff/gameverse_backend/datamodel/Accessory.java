package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.net.URL;
import java.util.Date;

@Entity
public class Accessory extends Product {

    public Accessory() { super(); }

    public Accessory(Long pid, String name, String description,
                   Float price, String image, Integer tax,
                   Long hpid, Integer amount, Date available)
    {

        super(pid, name, description, price, image, tax, hpid, amount, available);
    }
}
