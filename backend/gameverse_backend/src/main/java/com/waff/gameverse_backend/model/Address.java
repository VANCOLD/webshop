package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.AddressDto;
import com.waff.gameverse_backend.utils.DataTransferObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address implements DataTransferObject<AddressDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name="street")
    private String street;

    @Column(name="postalcode")
    private String postalCode;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @OneToMany(mappedBy="address")
    private List<User> users;

    @OneToMany(mappedBy="address")
    private List<Producer> producers;

    public Address(AddressDto addressDto) {
        this.city        = addressDto.getCity();
        this.postalCode  = addressDto.getPostalCode();
        this.street      = addressDto.getStreet();
        this.country     = addressDto.getCountry();
        this.users       = addressDto.getUsers().isEmpty() ? new ArrayList<User>() : addressDto.getUsers().stream().map(User::new).toList();
        this.producers   = addressDto.getProducers().isEmpty() ? new ArrayList<Producer>() : addressDto.getProducers().stream().map(Producer::new).toList();
    }

    @Override
    public AddressDto convertToDto() {
        return new AddressDto(id, street, postalCode, city, country,
            users.stream().map(User::convertToSimpleDto).toList(),
            producers.stream().map(Producer::convertToDto).toList());
    }

}
