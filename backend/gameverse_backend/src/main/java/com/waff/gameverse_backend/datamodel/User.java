package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="User")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class User
{

    @Id
    @GeneratedValue
    @Column(name ="uid")
    private Long uid;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="admin")
    private Boolean admin;

}
