package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="privileges")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Privileges {

    @Id
    @Column(name="privID")
    private Long privID;

    @Column(name="name")
    private String name;

    @ManyToMany
    private Set<Roles> roles;

}
