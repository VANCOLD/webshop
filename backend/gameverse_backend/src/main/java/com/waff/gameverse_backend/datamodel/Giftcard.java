package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.Entity;

import java.util.Date;


@Entity
public class Giftcard extends Product
{
    public Giftcard() {
        super();
    }

    public Giftcard(Long pid, String name, String description,
                       Float price, String image, Integer tax,
                       Long hpid, Integer amount, Date available)
    {

        super(pid, name, description, price, image, tax, hpid, amount, available);
    }
}
