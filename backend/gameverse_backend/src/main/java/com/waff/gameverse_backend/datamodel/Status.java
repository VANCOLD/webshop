package com.waff.gameverse_backend.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Status")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Status
{

    @Id
    @GeneratedValue
    @Column(name="sid")
    private Long sid;

    @Column(name="status")
    private String status;
}
