package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.AddressDto;
import com.waff.gameverse_backend.utils.DataTransferObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address implements DataTransferObject<AddressDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name="street")
    private String street;

    @Column(name="plz")
    private String postalCode;

    @Column(name="city")
    private String city;

    @Override
    public AddressDto convertToDto() {
        return new AddressDto(id, street, postalCode, city);
    }

}