package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "Genre")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Genre
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gid")
    private Long gid;


    @NotBlank
    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name;

}
