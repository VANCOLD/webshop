package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Console")
public class Console extends Product
{
    public Console() {
        super();
    }
}
