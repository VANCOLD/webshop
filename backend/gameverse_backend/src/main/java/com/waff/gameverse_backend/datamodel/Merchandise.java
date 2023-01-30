package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Merchandise")
public class Merchandise extends Product
{
    public Merchandise() {
        super();
    }
}
