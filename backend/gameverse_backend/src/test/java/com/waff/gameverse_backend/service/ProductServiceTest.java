package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.ProductDto;
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

        String product1 = "Banjo Kazooie";

        String product2 = "The Legend Of Zelda: Breath Of The Wild";

        var testCase1 = this.productService.save(product1);
        assertThat(testCase1.getName()).isEqualTo(product1);

        assertThrows(IllegalArgumentException.class, () -> this.productService.save(product2));
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
