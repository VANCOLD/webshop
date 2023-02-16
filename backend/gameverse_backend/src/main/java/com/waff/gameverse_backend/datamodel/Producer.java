package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
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

    @Column(name = "name")
    private String name;

}
