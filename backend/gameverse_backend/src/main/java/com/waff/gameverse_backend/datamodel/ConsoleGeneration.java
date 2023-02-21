package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "ConsoleGeneration")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class ConsoleGeneration
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cg_id")
    private Long cg_id;


    @NotBlank @NotNull @NotEmpty
    @Column(name = "name")
    private String name;


    @NotBlank @NotNull @NotEmpty
    @Column(name =  "iconPath")
    private String iconPath;
}
