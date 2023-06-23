package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "console_generation")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class ConsoleGeneration
{

    @Id
    @Column(name = "cg_id")
    private Long cgid;

    @Column(name = "name")
    private String name;

    @Column(name =  "icon_path")
    private String iconPath;
}
