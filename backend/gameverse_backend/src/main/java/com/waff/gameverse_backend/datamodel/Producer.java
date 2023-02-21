package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name="producer")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Producer
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "h_id")
    private Long h_id;


    @NotBlank @NotNull @NotEmpty
    @Column(name = "name")
    private String name;

}
