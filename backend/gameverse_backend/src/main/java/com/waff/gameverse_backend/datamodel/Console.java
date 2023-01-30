package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.net.URL;
import java.util.Date;

@Entity
public class Console extends Product
{
    public Console() {
        super();
    }

    public Console(Long pid, String name, String description,
                       Float price, String image, Integer tax,
                       Long hpid, Integer amount, Date available)
    {

        super(pid, name, description, price, image, tax, hpid, amount, available);
    }
}
