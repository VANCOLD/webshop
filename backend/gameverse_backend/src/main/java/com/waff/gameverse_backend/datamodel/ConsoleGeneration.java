package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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


    @NotBlank @NotNull @NotEmpty
    @Column(name = "name")
    private String name;


    @NotBlank @NotNull @NotEmpty
    @Column(name =  "icon_path")
    private String iconPath;
}
