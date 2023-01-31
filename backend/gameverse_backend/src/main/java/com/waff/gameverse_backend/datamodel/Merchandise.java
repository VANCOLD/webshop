package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.Entity;

import java.net.URL;
import java.util.Date;

@Entity
public class Merchandise extends Product
{
    public Merchandise() {
        super();
    }

    public Merchandise(Long pid, String name, String description,
                Float price, String image, Integer tax,
                Producer producer, Long hpid, Integer amount, Date available)
    {

        super(pid, name, description, price, image, tax, hpid, amount, available);
    }
}
