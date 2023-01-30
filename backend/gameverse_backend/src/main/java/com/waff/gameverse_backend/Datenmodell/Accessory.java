package com.waff.gameverse_backend.Datenmodell;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Accessory")
public class Accessory extends Product {

    public Accessory() { super(); }
}
