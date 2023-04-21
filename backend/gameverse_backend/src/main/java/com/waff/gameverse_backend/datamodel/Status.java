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
@Table(name="Status")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Status
{

    @Id
    @Column(name="sid")
    private Long sid;

    @NotBlank @NotNull @NotEmpty
    @Column(name="status")
    private String status;
}
