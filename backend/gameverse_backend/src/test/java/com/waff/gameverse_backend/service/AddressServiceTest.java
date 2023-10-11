package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.AddressDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(AddressService.class)
@ActiveProfiles("test")
public class AddressServiceTest {
    
    @Autowired
    private AddressService addressService;

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist die Kategorie Games
        Long addressId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long addressId2 = 1000L;

        var testCase1 = this.addressService.findById(addressId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.addressService.findById(addressId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.addressService.findAll();
        assertThat(testCase1.size()).isEqualTo(11);
    }

    @Test
    void saveTest() {

        AddressDto address1 = new AddressDto(0L, "Peterstraße 12","1210","Wien","Österreich");

        AddressDto address2 = new AddressDto(1L, "Peterstraße 12","1210","Wien","Österreich");


        var testCase1 = this.addressService.save(address1);
        assertThat(testCase1.getStreet()).isEqualTo(address1.getStreet());

        assertThrows(IllegalArgumentException.class, () -> this.addressService.save(address2));
    }

    @Test
    void updateTest() {
        AddressDto address = new AddressDto(100L, "Peterstraße 12","1210","Wien","Österreich");
        assertThrows(NoSuchElementException.class,() -> this.addressService.update(address));

        address.setId(1L);
        var updatedAddress = this.addressService.update(address);
        assertThat(updatedAddress.convertToDto()).isEqualToComparingFieldByField(address);

        address.setStreet("");
        assertThrows(IllegalArgumentException.class, () -> this.addressService.update(address));

    }

    @Test
    void deleteTest() {

        // Address Games
        Long address1 = 1L;

        // Nicht existierendes Address
        Long address2 = 1000L;

        var checkAddress = addressService.findById(address1);

        // Lösche des Eintrages der Rolle user
        var testCase1 = this.addressService.delete(address1);
        assertThat(testCase1.getStreet()).isEqualTo("Johnstraße 12");

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.addressService.findById(address1));

        // Dieser Aufruf sollte einen Fehler werfen da es kein Address mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.addressService.delete(address2));
    }
}
