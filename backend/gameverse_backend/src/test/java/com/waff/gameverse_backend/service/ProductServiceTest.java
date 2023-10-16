package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.*;
import com.waff.gameverse_backend.enums.EsrbRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testclass für ProductService Service. Benutzt die Daten des data.sql!
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(ProductService.class)
@ActiveProfiles("test")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist das Produkt The Legend Of Zelda: Breath Of The Wild
        Long productId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long productId2 = 1000L;

        var testCase1 = this.productService.findById(productId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.productService.findById(productId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.productService.findAll();
        assertThat(testCase1.size()).isEqualTo(7);
    }

    @Test
    void findByNameTest() {

        // Das Product mit den Namen Playstation 5 existiert
        String product1 = "The Legend Of Zelda: Breath Of The Wild";
        // Das Product Xbox 360 hingegen nicht!
        String product2 = "Banjo Kazooie";

        // Hier erwarten wir das wir das Product zurück bekommen die wir suchen
        var testCase1 = this.productService.findByName(product1);
        assertThat(testCase1.getName()).isEqualTo(product1);

        // Da es keine Product mit den Namen Banjo Kazooie gibt sollte es einen Fehler werfen!
        assertThrows(NoSuchElementException.class, () -> this.productService.findByName(product2));
    }

    @Test
    void saveTest() {

        // Already existing product, should return conflict!
        ProductDto testCase1 = new ProductDto(
                1L, "The Legend Of Zelda: Breath Of The Wild","Cool zelda", 60.00, "Cool Image",  20, 200, "1234",
                LocalDateTime.of(2022,1,1,12,0), EsrbRating.EVERYONE.getName(),
                new ConsoleGenerationDto(null, "Nintendo Switch"), new CategoryDto(null, "Games"),
                new ProducerDto(null, "Nitendo",  new AddressDto(null, "test", "test", "test", "test")),
                List.of(new GenreDto(null, "Adventure")));

        ProductDto testCase2 = new ProductDto(
                1000L, "Froggyo","froggo", 120.00, "Cool frogs",  20, 2, "asdasd",
                LocalDateTime.of(2024,1,1,12,0), EsrbRating.EVERYONE.getName(),
                new ConsoleGenerationDto(2L, "Playstation 5"), new CategoryDto(1L, "Games"),
                new ProducerDto(2L, "Sony",  new AddressDto(null, "test", "test", "test", "test")),
                List.of(new GenreDto(9L, "Survival & Horror")));


        var product1 = this.productService.save(testCase2);
        assertThat(testCase2.getName()).isEqualTo(product1.getName());

        assertThrows(IllegalArgumentException.class, () -> this.productService.save(testCase1));
    }

    @Test
    void updateTest() {

        ProductDto product = new ProductDto();
        product.setId(1000L);
        product.setName("test");

        assertThrows(NoSuchElementException.class,() -> this.productService.update(product));

        product.setId(1L);
        var updatedRole = this.productService.update(product);
        assertThat(updatedRole.getName()).isEqualTo(product.getName());

        product.setName("");
        assertThrows(IllegalArgumentException.class, () -> this.productService.update(product));

    }

    @Test
    void deleteTest() {

        // Product view_profile
        Long product1 = 1L;

        // Nicht existierendes Product
        Long product2 = 1000L;

        // Lösche des Eintrages der Rolle user
        var testCase1 = this.productService.delete(product1);
        assertThat(testCase1.getName()).isEqualTo("The Legend Of Zelda: Breath Of The Wild");

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.productService.findById(product1));

        // Dieser Aufruf sollte einen Fehler werfen da es kein Product mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.productService.delete(product2));
    }
}
