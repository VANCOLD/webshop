package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Roles {

    @Id
    @Column(name="roleID")
    private Long roleID;

    @Column(name="name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Privileges> privilege;

    @ManyToOne
    @JoinColumn(name="uid", nullable=false)
    private User user;
}
