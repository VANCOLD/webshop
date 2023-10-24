package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByStreetAndCityAndPostalCodeAndCountry(String street, String city, String postalCode, String country);

    Address findByStreetAndCityAndPostalCodeAndCountry(String street, String city, String postalCode, String country);
}
