package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull @NotBlank @NotEmpty
    @Column(name="username")
    private String username;

    @NotNull @NotBlank @NotEmpty
    @Column(name="password")
    private String password;

    @NotNull
    @Column(name="admin")
    private Boolean admin;

}
